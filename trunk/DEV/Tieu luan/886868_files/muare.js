function changeUrl(pageno, redirectUrl) {
	var redirect;
	redirect = redirectUrl+'&page='+pageno.value;
	document.location.href = redirect;
}

function pushItem2Cookies(newthreadid, newthreadtitle, newtitle_clean)
{
	var cookie_value= '';
	var COOKIE_NAME = 'recent_item_cookie';
	var json_item 	= '{"threadid":"'+newthreadid+'","threadtitle":"'+newthreadtitle+'","title_clean":"'+newtitle_clean+'"}';
	var cookie_data = getCookie(COOKIE_NAME);
	
	if(cookie_data == '')
	{
		cookie_value = json_item;
		setCookie(COOKIE_NAME, cookie_value, 365);
	}
	else
	{
		json_data = eval("[" + cookie_data + "]");
		var j = 0;
        var tmp_arr = new Array();
		for (var i = 0; i < json_data.length; i++){
			if (newthreadid == json_data[i].threadid){
				j++;
			}
			else{
				tmp_arr[i] = '{"threadid":"'+json_data[i].threadid+'","threadtitle":"'+json_data[i].threadtitle+'","title_clean":"'+json_data[i].title_clean+'"}';
			}
		}
		if(j == 0)
		{
			if (parseInt(tmp_arr.length) > 9)
				tmp_arr.shift();
			cookie_value = tmp_arr.join(",") + ',' + json_item;
			setCookie(COOKIE_NAME, cookie_value, 365);
		}
	}
		
}