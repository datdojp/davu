var xmlhttp;
function censorship_reply(userid, threadid, postid) {
    xmlhttp = GetXmlHttpObject();

    if (xmlhttp == null) {
        alert("Your browser does not support AJAX!");
        return;
    }

    var url = "managerreply.php";
    url += "?do=censorship&p=" + postid;
    xmlhttp.onreadystatechange = function () {
        set_stateChanged('censorship_div_reply_' + postid);
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send(null);
}

function uncensorship_reply(userid, threadid, postid) {
    xmlhttp = GetXmlHttpObject();

    if (xmlhttp == null) {
        alert("Your browser does not support AJAX!");
        return;
    }

    var url = "managerreply.php";
    url += "?do=censorship&p=" + postid;
    xmlhttp.onreadystatechange = function () {
        set_stateChanged('censorship_div_reply_' + postid);
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send(null);
}

function hide_sign(userid, threadid, postid) {
    xmlhttp = GetXmlHttpObject();

    if (xmlhttp == null) {
        alert("Your browser does not support AJAX!");
        return;
    }

    var url = "managerreply.php";
    url += "?do=hidesig&p=" + postid;
    xmlhttp.onreadystatechange = function () {
        set_stateChanged('hide_sign_div_' + postid);
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send(null);
}

//hide reply
function hide_reply(userid, threadid, postid) {
    xmlhttp = GetXmlHttpObject();

    if (xmlhttp == null) {
        alert("Your browser does not support AJAX!");
        return;
    }

    var url = "managerreply.php?do=visible";
    url += "&p=" + postid;
    xmlhttp.onreadystatechange = function () {
        set_stateChanged('hide_reply_div_' + postid);
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send(null);
}

//change tác giả
function change_username(userid, threadid, postid) {
    xmlhttp = GetXmlHttpObject();
    if (xmlhttp == null) {
        alert("Your browser does not support AJAX!");
        return;
    }

    var frm = document.getElementById('change_username_form_' + postid);
    var url = "managerreply.php?do=chuser";
    url = url + "&username=" + frm['userid_of_reply'].value;
    url = url + "&p=" + postid;
    xmlhttp.onreadystatechange = function () {
        set_stateChanged('change_div_reply_' + postid);
    };

    xmlhttp.open("GET", url, true);
    xmlhttp.send(null);

}

//thay đổi chủ đề
function change_threadid(userid, threadid, postid) {
    xmlhttp = GetXmlHttpObject();
    if (xmlhttp == null) {
        alert("Your browser does not support AJAX!");
        return;
    }

    var frm = document.getElementById('change_threadid_form_' + postid);
    var url = "managerreply.php?do=chthread";
    url = url + "&t=" + frm['threadid_of_reply'].value;
    url = url + "&p=" + postid;
    xmlhttp.onreadystatechange = function () {
        set_stateChanged('change_div_thread_' + postid);
    };

    xmlhttp.open("GET", url, true);
    xmlhttp.send(null);

}

function GetXmlHttpObject() {
    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        return new XMLHttpRequest();
    }
    if (window.ActiveXObject) {
        // code for IE6, IE5
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
    return null;
}

function set_stateChanged(element_id) {
    if (xmlhttp.readyState == 4) {
		var obj = document.getElementById(element_id);
		if(obj)
			obj.innerHTML = xmlhttp.responseText;
    }
}
//Xóa reply added by dacdv
function ajax_delete_post(url, postid) {
	if(confirm('Bạn có chắc muốn xóa bài viết này ?')){
		xmlhttp = GetXmlHttpObject();
		if (xmlhttp == null) {
			alert("Your browser does not support AJAX!");
			return false;
		}
		xmlhttp.onreadystatechange = function () {
			set_stateDeletePost(url, postid);
		};
		xmlhttp.open("GET", url, true);
		xmlhttp.send(null);
	}
	return false;
}
function set_stateDeletePost(url, postid) {
    if (xmlhttp.readyState == 4) {
      var obj =	document.getElementById("post" + postid);
	  if(obj){
		  obj.style.display = 'none';
	  }
	}
}
function delete_reply(userid, threadid, postid) {
	var url = "editpost.php?do=editpost&p=" + postid + "&delete=1";
	window.location.href= url;
	/*
	xmlhttp = GetXmlHttpObject();
	if (xmlhttp == null) {
		alert("Your browser does not support AJAX!");
		return false;
	}
	xmlhttp.onreadystatechange = function () {
		set_stateDeleteReply();
	};
	xmlhttp.open("POST", url, true);
	xmlhttp.send(null);	
	*/
}
/*
function set_stateDeletePost() {
    if (xmlhttp.readyState == 4) {
      alert('Đã xóa bài viết thành công !');
	}
}
*/
//end added by dacdv