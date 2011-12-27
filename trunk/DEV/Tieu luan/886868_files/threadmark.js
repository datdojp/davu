var xmlhttp_threadmark;
function threadmark(str, userid, forumid)
{
	if (userid==0)
	{ 
		alert('Bạn chưa đăng nhập, để đánh dấu chủ đề vui lòng đăng nhập');
	}else{
xmlhttp_threadmark=GetXmlHttpObject();
if (xmlhttp_threadmark==null)
  {
  alert ("Your browser does not support AJAX!");
  return;
  }
    
var url="threadmark.php";
url=url+"?threadid="+str;
url=url+"&userid="+userid;
url=url+"&forumid="+forumid;
url=url+"&sid="+Math.random();
xmlhttp_threadmark.onreadystatechange=stateChanged;
xmlhttp_threadmark.open("GET",url,true);
xmlhttp_threadmark.send(null);
	}
}

function stateChanged()
{
if (xmlhttp_threadmark.readyState==4)
{
	document.getElementById("mark_div").innerHTML=xmlhttp_threadmark.responseText;
	alert('Chủ đề đã được đánh dấu');
}
}

function GetXmlHttpObject()
{
if (window.XMLHttpRequest)
  {
  // code for IE7+, Firefox, Chrome, Opera, Safari
  return new XMLHttpRequest();
  }
if (window.ActiveXObject)
  {
  // code for IE6, IE5
  return new ActiveXObject("Microsoft.XMLHTTP");
  }
return null;
}