<font face='Gill Sans MT' size='3'>
How To<br>
<br>
<br>
<h1>Connect to an Sftp Server</h1>

OpenSFTP enables you to connect to an sftp server by using two types of authentication - public key and password. The following table lists the connection methods for each authentication type.<br>
<table><thead><th> <b>Authentication Type</b> </th><th> <b>API</b> (methods of <code>net.sf.opensftp.SftpUtil</code>) </th></thead><tbody>
<tr><td> publickey                  </td><td> <code>public SftpSession connect(String host, String user, String identityFile, int strictHostKeyChecking) throws SftpException</code><br><code>public SftpSession connect(String host, String user, String passphrase, String identityFile, int strictHostKeyChecking) throws SftpException</code><br><code>public SftpSession connect(String host, int port, String user, String passphrase, String identityFile, int strictHostKeyChecking, int timeout) throws SftpException</code> </td></tr>
<tr><td> password                   </td><td> <code>public SftpSession connectByPasswdAuth(String host, String user, String password, int strictHostKeyChecking) throws SftpException</code><br><code>public SftpSession connectByPasswdAuth(String host, int port, String user, String password, int strictHostKeyChecking, int timeout) throws SftpException</code> </td></tr></tbody></table>

The <code>port</code> parameter defaults to 22 if not specified.<br>
<br>
Available values for the <code>strictHostKeyChecking</code> parameter are <code>STRICT_HOST_KEY_CHECKING_OPTION_ASK</code>, <code>STRICT_HOST_KEY_CHECKING_OPTION_YES</code> and <code>STRICT_HOST_KEY_CHECKING_OPTION_NO</code>, corresponding to <code>ask</code>, <code>yes</code> and <code>no</code>, respectively. If you don't know what <code>StrictHostKeyChecking</code> is for, please refer to the famous OpenSSH project.<br>
<br>
The <code>timeout</code> parameter is the timeout in milliseconds.<br>
<br>
The <code>identityFile</code> parameter points out where your private keys are stored. If you specify a value begining with '~' and a following directory separator ('/' or '\\'), the '~' is treated as your home folder.<br>
<br>
The <code>passphrase</code> paramter defaults to <code>""</code> (an empty string) which means empty (no) passphrase.<br>
<br>
The returned <code>net.sftp.opensftp.SftpSession</code> object is in charge of maintaining the communication between you and the sftp server and must be passed through when manipulating the sftp server (see the next session).<br>
<br>
Here is an example of connection.<br>
<pre><code>SftpUtil util = SftpUtilFactory.getSftpUtil(); <br>
SftpSession session = null; <br>
String host = ...; <br>
String user = ...; <br>
String password = ...; <br>
 <br>
try { <br>
  //connect <br>
  session = util.connectByPasswdAuth(host, user, password, <br>
      SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO); <br>
} catch (SftpException e) { <br>
  e.printStackTrace(); <br>
}<br>
</code></pre>

Please note that here I use <code>net.sf.opensftp.SftpUtilFactory</code> to obtain a singleton instance of some concrete implementation of <code>net.sf.opensftp.SftpUtil</code>. It's highly recommended to always obtain an instance of <code>SftpUtil</code> in this way, thus enabling you to use lots of advanced, exciting features at no or very little cost. For further information about these features, please jump to the <a href='#Use_Advanced_Features.md'>Use Advanced Features</a> section.<br>
<br>
<h1>Disconnect from an Sftp Server</h1>
In contrast with connecting to an sftp server, disconnecting from an sftp server is rather simple. You just need an <code>SftpUtil</code> instance and an <code>SftpSession</code> instance you previously got when connecting, and do as follows.<br>
<pre><code>util.disconnect(session); //util is the SftpUtil instance you previously got, session is the SftpSession you previously got when connecting<br>
</code></pre>

<h1>Manipulate an Sftp Server</h1>



<h1>Use Advanced Features</h1>