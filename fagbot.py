import socket
import ssl
destination = 'irc.packetfire.org'
irc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
port = 6667


irc.connect((destination, port)) # Connect
irc.send('NICK fagbot\r\n')
irc.send('USER fagbot fagbot fagbot :fag\r\n')



while True: # active connection
	chan = '#packetfire'
        irc.send('JOIN %s \r\n' %chan) # Channel
        msg = irc.recv ( 4096 )
	def parse(msg):
		msg.split(' :')
        if msg.find ( 'PING' ) != -1:
		irc.send('PONG ' + msg.split() [1] + '\r\n')          
        if msg.find('please stop') != -1:
                irc.send('PRIVMSG %s :sorry.\r\n' %(chan))
                irc.send('QUIT\r\n')
        if msg.find("fagbot") != -1:
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))
                irc.send('PRIVMSG %s : happy birthday, iPaddy.\r\n' %(chan))


        print msg
