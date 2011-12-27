var xmlhttp;
function getAjaxObject() {
	try {
		return (typeof XMLHttpRequest != "undefined" ? new XMLHttpRequest : new ActiveXObject("Microsoft.XMLHTTP"))
	} catch (e) {
		return null;
	}
}



function toogle_class_btn_notify_bar(id, btnClassName) {
	elementId = document.getElementById(id);
	if (elementId.className == btnClassName) {
		elementId.className = btnClassName + 'Hover';
	} else if (elementId.className == btnClassName + 'Hover') {
		elementId.className = btnClassName;
	}
}

function get_update(id) {
	var cur_lang_status = getCookie('|mudim-settings');
	if (cur_lang_status == 16)
		document.getElementById("nb_lang_icon").className = 'nb_lang_icon_e';
	var last_update_time = getCookie('last_update_time');
	var d = new Date();
	var dtime = d.getTime();
	if ((document.getElementById('listReply').style.display == 'none' || document
			.getElementById('listReply').style.display == '')
			&& (document.getElementById('listInfo').style.display == 'none' || document
					.getElementById('listInfo').style.display == '')
			&& ((id != 0) || (last_update_time == '') || (parseInt(dtime)
					- parseInt(last_update_time) > 300000))) {
		var url = "notifybar.php?act=all&sid=" + getCookie('last_update_info')
				+ "&did=" + dtime;
		xmlhttp = getAjaxObject();
		document.getElementById('btn_show_feed').innerHTML = '<img hspace="3" vspace="3" valign="bottom" style="padding-top:5px;" src="images/muare/nb_loading.png"/>';
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				if (xmlhttp.responseText) {
					var str_respon = xmlhttp.responseText;
					respon = str_respon.split('|');
					if (respon[0] && respon[0] !== '0' && respon[0] !== 'undefined' && respon[0] !== '' && respon[0] !== '[]') {
					setCookie('last_update_time_value', parseInt(respon[0]));
					setCookie('last_update_info_value', (respon[1]));
					
						document.getElementById('show_reply_notify_bar').innerHTML = '';
						document.getElementById('btn_show_reply').innerHTML = '<img hspace="3" vspace="3" src="images/muare/thongbao.png"/><span id="notifications_count" style=""><strong>'
								+ respon[0] + '</strong></span>';
								
						if (respon[1] && respon[1] !== '0' && respon[1] !== 'undefined' && respon[1] !== '') {
							document.getElementById('show_info_notify_bar').innerHTML = '';
							document.getElementById('notifications_count_info').innerHTML = '<strong>Có <font color="red" size="3">'
									+ respon[1] + '</font> thông tin mới</strong>';
							document.getElementById('notifications_count_info').style.display = 'block';
							document.getElementById("notifybar_info_label").className = 'notifybar_info_label_yes';
						}
					}					
					document.getElementById('btn_show_feed').innerHTML = '<img hspace="3" vspace="3" src="images/muare/refe_1.png"/>';
				}
			}
		};
		xmlhttp.open("GET", url, true);
		xmlhttp.send(null);
		setCookie('last_update_time', dtime, 365);
	} else {
		if (document.getElementById('listReply').style.display == 'none'
				&& (document.getElementById('listInfo').style.display == 'none' || document
						.getElementById('listInfo').style.display == '')) {
			var last_update_time_value = getCookie('last_update_time_value');
			var last_update_info_value = getCookie('last_update_info_value');
			
			if (last_update_time_value && last_update_time_value !== '' && last_update_time_value !== 'undefined' && last_update_time_value !== '0'){
				document.getElementById('btn_show_reply').innerHTML = '<img hspace="3" vspace="3" src="images/muare/thongbao.png"/><span id="notifications_count" style=""><strong>'
						+ last_update_time_value + '</strong></span>';
			
				if (last_update_info_value && last_update_info_value != '' && last_update_info_value != 'undefined' && last_update_info_value != '0') {
					document.getElementById('show_info_notify_bar').innerHTML = '';
					document.getElementById('notifications_count_info').innerHTML = '<strong>Có <font color="red" size="3">'
							+ last_update_info_value
							+ '</font> thông tin mới</strong>';
					document.getElementById('notifications_count_info').style.display = 'block';
					document.getElementById("notifybar_info_label").className = 'notifybar_info_label_yes';
				}
			}
		}
	}
	setTimeout('get_update(1)', 300000);
}

function reset_notify_bar() {
	document.getElementById('listThreadview').style.display = 'none';
	document.getElementById('btn_show_threadview').className = 'notifyBarLeft';
	document.getElementById('listInfo').style.display = 'none';
	document.getElementById('btn_show_info').className = 'notifyBarLeft';
	document.getElementById('btn_show_lang').className = 'notifyBarLeft';
	if (document.getElementById('btn_show_reply')) {
		document.getElementById('btn_show_reply').className = 'notifyBarRight';
		document.getElementById('listReply').style.display = 'none';
	}
}

function change_language_notify_bar() {
	if (document.getElementById('nb_lang_icon').className == 'nb_lang_icon_v') {
		document.getElementById("nb_lang_icon").className = 'nb_lang_icon_e';
	} else {
		document.getElementById("nb_lang_icon").className = 'nb_lang_icon_v';
	}
	Mudim.Toggle();
}

function get_threadview_notify_bar() {
	get_notify_bar('threadview', 'listThreadview', 'btn_show_threadview',
			'threadview', 'show_threadview_notify_bar');
}

function get_reply_notify_bar() {
	setCookie('last_update_time_value', '');
	document.getElementById('btn_show_reply').innerHTML = '<img hspace="3" vspace="3" src="images/muare/thongbao1.png"/>';
	get_notify_bar('reply', 'listReply', 'btn_show_reply', 'reply',
			'show_reply_notify_bar');
}

function get_info_notify_bar() {
	setCookie('last_update_info_value', '');
	document.getElementById("notifybar_info_label").className = 'notifybar_info_label_no';
	document.getElementById('notifications_count_info').style.display = 'none';
	get_notify_bar('info', 'listInfo', 'btn_show_info', 'info',
			'show_info_notify_bar');
	var d = new Date();
	var dtime = d.getTime();
	setCookie('last_update_info', parseInt(dtime) / 1000, 365);
}

function get_notify_bar(type, blockid, btid, act, contentid) {
	if (document.getElementById(blockid).style.display == ""
			|| document.getElementById(blockid).style.display == "none") {
		reset_notify_bar();
		document.getElementById(blockid).style.display = 'block';
		if (type == 'threadview') {
			document.getElementById(btid).className = 'notifyBarLeftClicked';
			var cookie_data = getCookie('recent_item_cookie');
			var content = '';
			if (cookie_data != "") {
				var json_data = eval("[" + cookie_data + "]");
				for ( var i = 0; i < json_data.length; i++) {
					if (i % 2 == 0) {
						background_color = '#FFFFFF';
						//background_img = 'mess_notify_old.png';
					} else {
						background_color = 'rgb(245,245,245)';
						//background_img = 'mess_notify_old.png';
					}
					content += '<div class="notify_bar_text" style="background:url(images/muare/mess_notify_old.png) 8px 8px no-repeat; background-color:'
							+ background_color
							+ '; padding:0 10px 0 23px;"><div style="padding-bottom:5px;padding-top:5px;"><a href="'
							+ json_data[i]['title_clean']
							+ '/'
							+ json_data[i]['threadid']
							+ '">'
							+ json_data[i]['threadtitle'] + '</a></div></div>';
				}
			} else
				content = '<div style="text-align:center; padding: 3px 0 3px"><i>không có thông tin</i></div>';
			document.getElementById(contentid).innerHTML = content;
		} else {
			if (type == 'reply')
				document.getElementById(btid).className = 'notifyBarRightClicked';
			else
				document.getElementById(btid).className = 'notifyBarLeftClicked';
			if (document.getElementById(contentid).innerHTML == '') {
				document.getElementById(contentid).innerHTML = '<center><img hspace="7" vspace="7" valign="bottom" style="padding-top:5px;" src="images/muare/nb_loading.png"/></center>';
				var randomnumber = Math.random();
				var flag_new_notify = getCookie('flag_new_notify'); //
				var url = "notifybar.php?act=" + act + "&sid=" + randomnumber // +"&flag="+flag_new_notify; // when use cookie
				xmlhttp = getAjaxObject();
				xmlhttp.onreadystatechange = function() {
					showResultBox(type, blockid, btid, contentid);
				};
				xmlhttp.open("GET", url, true);
				xmlhttp.send(null);
				//reset flag new notify after show
				//setCookie('flag_new_notify', "0");
			}
		}
	} else {
		fn_close_nbar(blockid);
		if (type == 'reply')
			document.getElementById(btid).className = 'notifyBarRight';
		else
			document.getElementById(btid).className = 'notifyBarLeftHover';
	}
}

function showResultBox(type, blockid, btid, contentid) {
	if (xmlhttp.readyState == 4) {
		if (xmlhttp.responseText && xmlhttp.responseText != '[]') {
			var json_data = eval(xmlhttp.responseText);
			content = '';
			for (i in json_data) {
				if (i % 2 == 0) {
					background_color = '#FFFFFF';
				} else {
					background_color = 'rgb(245,245,245)';
				}
				background_img = 'mess_notify_old.png';

				if (type == 'reply') {
					if (json_data[i]['type'] == '0')
						div_text = ' phản hồi';
					else if (json_data[i]['type'] == 1)
						div_text = ' nhắc tới bạn';
					else
						div_text = ' trả lời bạn';
					if (json_data[i]['read'] == 'new') {
						background_color = '#FFFFCC';
						background_img = 'mess_notify_new.png';
					}
					if (json_data[i]['type'] == 3)
						content += '<div class="notify_bar_text" style="background:url(images/muare/'
							+ background_img
							+ ') 8px 8px no-repeat; background-color:'
							+ background_color
							+ '; padding:0 10px 0 23px;"><div style="padding-bottom:5px;padding-top:5px;">'
							+ json_data[i]['message']
							+ ' - <span style="color: rgb(128, 128, 128); font-size: 9px; padding:3pt"><i>lúc '
							+ json_data[i]['created']
							+ '</i></div></div>';
					else				
					
					
					if (json_data[i]['type'] == 0
							&& json_data[i]['total_reply'] > 1)
						content += '<div class="notify_bar_text" style="background:url(images/muare/'
								+ background_img
								+ ') 8px 8px no-repeat; background-color:'
								+ background_color
								+ '; padding:0 10px 0 23px;"><div style="padding-bottom:5px;padding-top:5px;">Có '
								+ json_data[i]['total_reply']
								+ ' người'
								+ div_text
								+ ' trong <a href="'
								+ json_data[i]['forum']
								+ '/p-'
								+ json_data[i]['min_postid']
								+ '#post'
								+ json_data[i]['min_postid']
								+ '">'
								+ json_data[i]['title'] + '</a></div></div>';
					else
						content += '<div class="notify_bar_text" style="background:url(images/muare/'
								+ background_img
								+ ') 8px 8px no-repeat; background-color:'
								+ background_color
								+ '; padding:0 10px 0 23px;"><div style="padding-bottom:5px;padding-top:5px;"><a href="member-'
								+ json_data[i]['postuserid']
								+ '" target="_blank">'
								+ json_data[i]['postusername']
								+ '</a>'
								+ div_text
								+ ' trong <a href="'
								+ json_data[i]['forum']
								+ '/p-'
								+ json_data[i]['postid']
								+ '#post'
								+ json_data[i]['postid']
								+ '">'
								+ json_data[i]['title']
								+ '</a> - <span style="color: rgb(128, 128, 128); font-size: 9px; padding:3pt"><i>lúc '
								+ json_data[i]['created']
								+ '</i></span></div></div>';
				}
				if (type == 'info') {
					var last_update_info = getCookie('last_update_info');
					if (parseInt(last_update_info) < json_data[i]['dateline'])
						background_color = '#FFFFCC';
					background_img = 'mess_notify_new.png';
					content += '<div class="notify_bar_text" style="background:url(images/muare/'
							+ background_img
							+ ') 8px 8px no-repeat; background-color:'
							+ background_color
							+ '; padding:0 10px 0 23px;"><div style="padding-bottom:5px;padding-top:5px;"><a href="'
							+ json_data[i]['url']
							+ '">'
							+ json_data[i]['title'] + '</a></div></div>';
				}
			}
			document.getElementById(contentid).innerHTML = content;
		} else
			document.getElementById(contentid).innerHTML = '<div style="text-align:center; padding: 3px 0 3px"><i>không có thông tin</i></div>';
	}
}

function fn_close_nbar(id) {
	document.getElementById(id).style.display = 'none';
	if (document.getElementById('btn_show_reply'))
		document.getElementById('btn_show_reply').className = 'notifyBarRight';
}

function set_threadview_cookies() {}

function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=");
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1;
			c_end = document.cookie.indexOf(";", c_start);
			if (c_end == -1)
				c_end = document.cookie.length;
			return unescape(document.cookie.substring(c_start, c_end));
		}
	}
	return "";
}

function setCookie(c_name, value, expiredays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	document.cookie = c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
			+ ";path=/";
}