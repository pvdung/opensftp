<font face='Gill Sans MT' size='3'>
<p align='left'><font color='blue' size='4'>
Features<br>
</font></p>
<hr />
The following is a list of features of OpenSFTP:<br>
<ul><li>OpenSFTP is in pure Java, so it's platform-independent. It's known to work with JDK 1.5.0 or later.<br>
</li><li>SSH2 protocol supported.<br>
<ul><li>Userauth: password, publickey (DSA, RSA)<br>
</li><li>Host key type: ssh-dsa, ssh-rsa<br>
</li><li>passphrase: empty or not<br>
</li><li>Key exchange: diffie-hellman-group-exchange-sha1, diffie-hellman-group1-sha1<br>
</li><li>Cipher: blowfish-cbc,3des-cbc,aes128-cbc,aes192-cbc,aes256-cbc,aes128-ctr,aes192-ctr,aes256-ctr,3des-ctr,arcfour,arcfour128,arcfour256<br>
</li><li>MAC: hmac-md5, hmac-sha1, hmac-md5-96, hmac-sha1-96<br>
</li></ul></li><li>Most SFTP commands have been implemented. The leftover will be added in the short future.<br>
</li><li>Easy to use. You can seamlessly use it in your Java project without worrying about the output capturing and exception handling. OpenSFTP manages them well for you.<br>
</li><li>Easy to extend.<br>
<ul><li>You're free to construct your own implementation of OpenSFTP.<br>
</li><li>You're free to create various modules for OpenSFTP.<br>
</li><li>It's very easy to make your application which builds on OpenSFTP use another concrete implementation of OpenSFTP instead without interfering the application itself.<br>
</li><li>It's very easy to plug in or remove the out-of-box modules or modules created by yourself or provided by 3rd parties without interfering your application.<br>
</li></ul></li><li>Easy to troubleshoot. OpenSFTP uses log4j to record its running status including output capturing and exception handling. It's easy to change the logging level, change the logging pattern and even customize your own appender.