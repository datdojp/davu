function toggleMe(a) {
	jQuery('#' + a).toggle();
}

/*function formReset() {
	document.getElementById("login_form").reset();
}*/
/*function getGreeting() {
		now = new Date();
		theHour = now.getHours();
		if ((theHour > 4) && (theHour < 11)) { greeting = 'Chúc bạn một ngày tốt lành'; }
		else if ((theHour >= 11) && (theHour <= 12)) { greeting = 'Chúc bạn bữa trưa ngon miệng'; }
		else if ((theHour > 12) && (theHour <= 18)) { greeting = 'Chúc bạn một buổi chiều tốt lành'; }
		else if ((theHour > 18) && (theHour < 24)) { greeting = 'Chúc bạn buổi tối thoải mái '; }
		else { greeting = 'Chúc ngủ ngon...'; }
		document.write(greeting);
}*/
/*function joinMing()
{
	myWindow=window.open("http://id.ming.vn/Request/?site_key=31fc7d574a7b749dd0f8ca0296e41ef5&foreign_type=10&site_callback=http://dev.ttvnol.com/close_popup.php","_blank","toolbar=no,location=yes, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=400, height=400");
}*/

/*function showLoginBox(){
	var $ = jQuery;
	$('#login-popup-box').addClass('active');
}*/

/*function openNewWindow(mypage,w,h,myname){
	var winl = (screen.width-w)/2;
	var wint = (screen.height-h)/2;
	settings='height='+h+',width='+w+',top='+wint+',left='+winl+',scrollbars=no,toolbar=no,location=no,resizable=no'
	win=window.open(mypage,myname,settings)
	if(parseInt(navigator.appVersion) >= 4){win.window.focus();}
}*/

/*function showModalBox(){
	var $ = jQuery;
	('#mingid-modalbox').
}*/
var lastupdate = 0;
var myTinyScrollbar;
jQuery(document).ready(function($){
	/*$.cuteUpdate = function(){
		setTimeout(function(){
			$('#lst_ticker').append($('li:first-child',$('#lst_ticker')).clone()).append($('li:first-child',$('#lst_ticker')).clone());
			myScroll.tinyscrollbar_update('relative');
		},2000);
	}*/

	myTinyScrollbar = $('#ticker_panel').tinyscrollbar();//.initialize();

	$(".timeago").timeago();

	// fetch recent public feed
	getRecentFeed();
//	setInterval("getRecentFeed()",15000); // remember to modify memcache expiration time appropriately

	//ajax member positive
	$.get(BaseURL + "member.php",{
			'action':'get_rand_ten_member_positive_html',
			'ctrl'	:'misc'
		},function(result)
		{
			if (result != 'error'){
				$('div#member_positive').find('div.block-content').append(result);
				$('div#member_positive').find('img.loading').addClass('hide');
			}
		}
	);
	//end ajax member positive

	$(document).keyup(function(e){
		if (e.keyCode == 27){
			$('.ohyeah-pop-box').each(function(index, el){
				if ($(this).attr('id') != 'mingid-modalbox'){
					$(this).removeClass('dis-b').removeClass('active');
				}
			})
		}
	});

	//load embeded code
	/*$('.embed-wrapper').each(function(index){
		if ($(this).attr('class').indexOf('admicro') >= 0){
			var wrapper = $(this);
			//scriptElement = document.createElement( 'script' );
//			scriptElement.type = "text/javascript";
//			scriptElement.src = 'http://admicro1.vcmedia.vn/ads_codes/' + $(this).attr('id') + '.ads';
//			$(this).append(scriptElement);
			document.write = function(text){
				wrapper.append(text);
			}
			$.getScript(
				'http://admicro1.vcmedia.vn/ads_codes/' + wrapper.attr('id') + '.ads'
			);
		}
	});*/
});

function showMingLogin(){
	var $ = jQuery;
	$('#mingid-modalbox').addClass('dis-b');
	$('#mingid-modalbox iframe').attr('src', urlMingConnect);
	$('#mingid-modalbox .cute-close-window').click(function(){
		$('.ohyeah-pop-box').removeClass('dis-b');
	})
}

var gettingRecentFeed = false;
function getRecentFeed(){
	var $ = jQuery;
	if (!gettingRecentFeed){
		$('#ticker-loading').show();
		gettingRecentFeed = true;
		$.get(
			BaseURL + 'member.php',
			{
				action		: 'get_recent_feed',
				ctrl		: 'feed',
				lastupdate	: lastupdate
			},
			function(result){
				$('#ticker-loading').hide();
				if (result != 'error'){
					$('#lst_ticker').prepend(result);
					lastupdate = parseInt($('#lst_ticker li:first .ticker-time').attr('id'));
				}
				$(".timeago").timeago();
				$('#ticker_panel').tinyscrollbar_update('relative');
				gettingRecentFeed = false;
			}
		)
	}
}
