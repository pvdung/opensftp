<font face='Gill Sans MT' size='3'>
<p align='left'><font color='blue' size='4'>
Get OpenSFTP<br>
</font></p>
<hr />
The latest stable release:<br>
<br>
<a href='http://code.google.com/p/opensftp/downloads/detail?name=opensftp-0.3.0-jars.zip'>Binary JARs</a> (includes only necessary JARs)<br>
<br>
<a href='http://code.google.com/p/opensftp/downloads/detail?name=opensftp-0.3.0-bin.zip'>Package</a> (includes JARs, source, Javadoc, manuals, etc.)<br>
<br><br>
Older releases can be found <a href='ReleaseHistory.md'>here</a>.<br>
<p />
<p align='left'><font color='blue' size='4'>
Set Up the Class Path<br>
</font></p>
<hr />
Add the following JARs to your class path:<br>
<ul><li>opensftp-api-x.x.x.jar<br>
</li><li>opensftp-impl-x.x.x.jar<br>
</li><li>All JARs in the lib folder<br>
<p />
<p align='left'><font color='blue' size='4'>
The Simplest Example<br>
</font></p>
<hr />
Generally speaking, there're three steps to access and manipulate an sftp server.<br>
</li></ul><ol><li>Connect to an sftp server. Use the connection methods of <code>net.sf.opensftp.SftpUtil</code> to connect to an sftp server. The returned <code>net.sftp.opensftp.SftpSession</code> object is in charge of maintaining the communication between you and the sftp server and must be passed through when manipulating the sftp server (see below).<br>
</li><li>Manipulate the sftp server. Use the command methods (ls, pwd, cd, put, get, etc.) of <code>net.sf.opensftp.SftpUtil</code> to manipulate the sftp server.<br>
</li><li>Disconnect from the sftp server. Use the disconnection method of <code>net.sf.opensftp.SftpUtil</code> to disconnect from the sftp server.<br>
Here is an example.<br>
<pre><code>SftpUtil util = SftpUtilFactory.getSftpUtil();<br>
SftpSession session = null;<br>
String host = ...;<br>
String user = ...;<br>
String password = ...;<br>
<br>
try {<br>
  //connect<br>
  session = util.connectByPasswdAuth(host, user, password,<br>
      SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO);<br>
} catch (SftpException e) {<br>
  e.printStackTrace();<br>
}<br>
<br>
if (session == null)<br>
  return;<br>
<br>
util.help(session); //print the help information<br>
<br>
//OpenSftp provides a default logging interceptor to capture the outputs or error messages of commands.<br>
//If you want to handle it by yourself, as follows is a sample.<br>
SftpResult result = util.help(session);<br>
if(result.getSuccessFlag())<br>
  System.out.println(result.getExtension());<br>
else<br>
  System.out.println(result.getErrorMessage());<br>
<br>
//here we continue<br>
util.pwd(session); //print the working directory (current path)<br>
util.version(session); //print the sftp version information<br>
util.ls(session); //list the current path if path not specified<br>
util.mkdir(session, "tmp4sftp"); //make a directory 'tmp4sftp' under current path<br>
util.put(session, "D:/Received/README", "tmp4sftp"); //upload a file to a folder relative to the current path<br>
util.put(session, "D:/Received/README", "tmp4sftp/README2"); //upload a file and rename the copy<br>
util.get(session, "tmp4sftp/README2", "D:/Received"); //download a file to a specified local path<br>
util.get(session, "tmp4sftp/README2", "D:/Received/README3"); //download a file and rename the copy<br>
util.rename(session, "tmp4sftp/README2", "tmp4sftp/README3"); //rename a remote file<br>
util.rm(session, "tmp4sftp/*"); //remove files<br>
util.rmdir(session, "tmp4sftp"); //remove a directory<br>
util.cd(session, ".."); //change directory<br>
<br>
util.disconnect(session); //quit<br>
</code></pre></li></ol>

<br><br>
For more examples, please jump to the <a href='Examples.md'>Examples</a> page.<br>
<p />
<p align='left'><font color='blue' size='4'>
Where Next?<br>
</font></p>
<hr />
The OpenSFTP library is explored in more depth in <a href='Cookbook.md'>Cookbook</a>.<br>
The <a href='CheatSheet.md'>Cheat Sheet</a> is an overview of the entire OpenSFTP API.<br>
Besides, the <a href='Javadoc.md'>Javadoc</a> is a good helper.