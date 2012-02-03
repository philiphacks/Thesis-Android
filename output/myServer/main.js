var connect = require('connect');
var ws = require('websocket.io');
var session = require('./session');
var chat = require('./chat');

// commands:
//{"auth" : { "user" : "phil", "pass" : "foobar" } }
//{"chat" : { "user" : "phil", "message" : "testing the chat" } }

console.log('Starting main server');

function auth(info, connection, fn) {
	session.authenticate(info, connection, fn);
}

var http = connect.createServer(
	connect.logger(),
	connect.cookieParser(),
	connect.session({ secret: 'keyboard cat' }),
	connect.basicAuth(auth)
).listen(80);

var server = ws.attach(http);
var connections = [];

server.on('connection', function (socket) {
	console.log(socket);
	connections.push({'enabled' : 'false', 'user' : '', 'connection' : socket});
	socket.on('message', function (message) {
		console.log(message);
		for (var i = connections.length - 1; i >= 0; i--) {
			if (connections[i]['connection'] == socket) {
				if (connections[i]['enabled'] == 'false') {
					try {
						var msg = JSON.parse(message);
						if (msg['auth'] != "") {
							console.log('got auth message');
							auth(msg['auth'], connections[i], enableConnection);
						}	
					} catch (err) {
						console.log(err);
					}
				} else if (connections[i]['enabled'] == 'true') {
					// Authenticated. We can process the request.
					try {
						var msg = JSON.parse(message);
						if (msg['chat'] != "") {
							console.log(msg['chat']['user'] + ': ' + msg['chat']['message']);
							//chat.distributeMessage(connections);
							//chat.saveMessage(msg['chat']);
						}	
					} catch (err) {
						console.log(err);
					}
				}		
			}
		};
	});
	socket.on('close', function () {
		for (var i = connections.length - 1; i >= 0; i--) {
			if (connections[i]['connection'] == socket) {
				console.log('Closing and removing socket connection');
				connections.splice(i, 1);
			}
		};
	});
});

function enableConnection(c, user, error) {
	if (error) {
		c['connection'].send("Wrong username/password");
	} else {
		console.log('connection established successfully!');
		c['enabled'] = 'true';	
		c['user'] = user;
	}
}
