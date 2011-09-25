import socket 
import time 
from datetime import date
 
host='irc.packetfire.org'
channel='#botlab'
password='artfag'
nicks='otherbot'
 
def tpars(txt):
    q=txt.split('<span class="temp">') [1]
    temp=q.split(' C') [0]
    qq=txt.split('<span>') [1]
    wind=qq.split('</span>') [0]
    return temp, wind
 
def sendm(msg): 
    irc.send('PRIVMSG '+ channel + ' :' + str(msg) + '\r\n')
 
irc = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 
irc.connect((host, 6667)) 
 
irc.send('USER py host servname : winter\r\n') 
irc.send('NICK '+ str(nicks) +'\r\n')
 
while 1:
    text=irc.recv(2040)
    if not text:
        break
 
    if text.find('Message of the Day') != -1:
        irc.send('JOIN '+ channel +'\r\n')
 
    if text.find('+iwR') != -1:
        irc.send('NS IDENTIFY '+ str(password) +'\r\n')
 
    if text.find('PING') != -1: 
        irc.send('PONG ' + text.split() [1] + '\r\n')
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!quit') != -1: 
        irc.send('QUIT :eat dicks\r\n')
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!KICK') != 1:
        irc.send('JOIN '+ channel +'\r\n')
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!date') != -1:
        sendm('Date: '+ time.strftime("%a, %b %d, %y", time.localtime()))
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!time') != -1:
        sendm('Time: '+ time.strftime("%H:%M:%S", time.localtime()))
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!weather') != -1:
        tmp = text.split(':!weather')
        city = tmp[1].strip()
        reqest_str = '/laika_zinas/?city=' + city
        c = httplib.HTTPConnection("www.1188.lv")
        c.request("GET", reqest_str)
        ra = c.getresponse()
        datas = ra.read()
        temp, wind = tpars(datas)
        sendm('Temp: '+ temp +' C | Wind: '+ wind +' m/s')
        c.close()
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!say') != -1:
        says = text.split(':!say')
        sayse = says[1].strip()
        sendm('.:: '+ str(sayse) +' ::.')
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!join') != -1:
        joins = text.split(':!join')
        das = joins[1].strip()
        irc.send('JOIN '+ str(das) +'\r\n')
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!nick') != -1:
        nickname = text.split(':!nick')
        if len(nickname) < 2:
            pass
        else:
            nicknames = nickname[1].strip()
            irc.send('NICK '+ str(nicknames) +'\r\n')
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!dns') != -1:
        dns = text.split(':!dns')
        dnsf = dns[1].strip()
        dnserv = socket.gethostbyaddr(dnsf)
        sendm('DNS : '+ str(dnserv))
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!part') != -1:
        part = text.split(':!part')
        parts = part[1].strip()
        irc.send('PART '+ str(parts) +'\r\n')
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!voice') != -1:
        voice = text.split(':!voice')
        voices = voice[1].strip()
        irc.send('MODE '+ str(channel) +' +v '+ str(voices) +'\r\n')
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!devoice') != -1:
        devoice = text.split(':!devoice')
        devoices = devoice[1].strip()
        irc.send('MODE '+ str(channel) +' -v '+ str(devoices) +'\r\n')
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!wiki') != -1:
        wiki = text.split(':!wiki')
        wikis = wiki[1].strip()
        if len(wikis) < 1:
            sendm('Error ! Wrote Name ! Example : !wiki something')
        else:
            sendm('iki : http://en.wikipedia.org/wiki/'+ str(wikis))
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!topic') != -1:
        topi = text.split(':!topic')
        topic = topi[1].strip()
        irc.send('TOPIC #botlab^ :'+ str(topic) +'\r\n')
        if topic == topic:
            sendm('Topic change')
 
    if text.find('ICE!i.am@winter.mute PRIVMSG #botlab :!image') != -1:
        image = text.split(':!image')
        images = image[1].strip()
        if len(images) < 1:
            sendm('Error ! Wrote : !image world')
        else:
            sendm('images url : http://images.google.lv/images?um=1&hl=ru&q='+ images +'&btnG')

 
