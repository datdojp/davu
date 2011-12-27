/*
* Created by TT97IT.
* User: TT97IT
* Date: 25/10/2011
* Time: 17:59
* To change this template use File | Settings | File Templates.
*/

/*jQuery(function($){
$.left_menutop_pop_click();
});

(function($){
$.left_menutop_pop_click=function(){
var menu_item= $('li.menu-pop-item .menu-item','#navtop_menu');
menu_item.click(function(e){
e.preventDefault();
var bActive = $(this).parent().hasClass('active');
menu_item.parent().removeClass('active');
if(!bActive){$(this).parent().addClass('active');}
});
};
})(jQuery);*/
jQuery(document).ready(function($){
	$("body").click(function(){
		//        if(! mouse_is_inside) {
		$('#navtop_menu .active').removeClass('active');
		//        }
	});

	$("#menu_pop_pms").click(function(){
		$("#smallpop-pm").html('');
		window.location.href="/private.php";
	});
	/*$("#menu_pop_pms").click(function(e){
	$("#menu_pop_notify").removeClass('active');
	$("#menu_pop_game").removeClass('active');
	e.stopPropagation();
	if ($(this).hasClass('active')){ // box is opening
	$(this).removeClass('active');
	}else{
	$(this).addClass('active');

	var loading = $("li#menu_pop_pms").find('img.loading');
	loading.removeClass('hide');
	var content = $('li#menu_pop_pms').find('div.menu-pop-box:first').html();

	if ($.trim(content) == ''){
	$.get(BaseURL+"member.php",{
	'action':'showpm',
	'ctrl':'profile'
	},function(result)
	{
	if (result != 'error'){
	$("#smallpop-pm").remove();
	$("li#menu_pop_pms").find('div.menu-pop-box:first').append(result);
	}
	else{
	alert('Có lỗi trong quá trìn xử lý. Các bạn vui lòng thử lại sau !');
	}

	}
	);

	}
	loading.addClass('hide');
	}

	});*/
	//luyentrv
	$('li.mnuser-setting').hover(function(){
		$('li.menu-pop-item').removeClass('active');
	});


	/*$('body').keyup(function(e){
		if (e.keyCode == 27){
			$('div.ohyeah-pop-box').removeClass('dis-b');
		}
	});*/


	$(".menu-pop-box").click(function(e){
		e.stopPropagation();
	});

	$("#menu_pop_notify").click(function(e){
		$(".menu-pop-item").removeClass('active');
		e.stopPropagation();		
		
		if ($(this).hasClass('active')){ // box is opening
			$(this).removeClass('active');
		}else{
			$(this).addClass('active');
		}
		
		var content = $('li#menu_pop_notify').find('.pop-btn-viewall:first').html();
		if ($.trim(content) == ""){			

			loading = $("li#menu_pop_notify").find('img.loading');
			loading.removeClass('hide');
			var unread = parseInt($("#smallpop-notify span").text());
			if (isNaN(unread)){
				unread = 0;
			}
			$.get(
				BaseURL+"member.php",
				{
					'action':'get_notification',
					'ctrl'	:'notification',
					'unread':unread
				},
				function(result){
					if (result != 'error'){
						$("#smallpop-notify").remove();
						$("li#menu_pop_notify").find('div.menu-pop-box:first').append(result);
						$('.timeago').timeago();
					}
					else{
						alert('Có lỗi trong quá trình xử lý. Các bạn vui lòng thử lại sau !');
					}
					//jQuery('.btn_add_friend').prepend(result);
					loading.addClass('hide');
				}
				
			);
		}
		
	});

	$("#menu_pop_game").click(function(e){
		$(".menu-pop-item").removeClass('active');
		e.stopPropagation();
		if ($(this).hasClass('active')){ // box is opening
			$(this).removeClass('active');
		}else{
			$(this).addClass('active');
		}

		var content = $('li#menu_pop_game').find('div.menu-pop-box:first').html();
		if ($.trim(content) == ''){
			loading = $("li#menu_pop_notify").find('img.loading');
			loading.removeClass('hide');
			var unread = parseInt($("#smallpop-apprequest span").text());
			if (isNaN(unread)){
				unread = 0;
			}
			$.get(
				BaseURL + "member.php",
				{
					'action': 'get_user_apprequest',
					'ctrl'	: 'apprequest',
					'unread': unread
				},
				function(result){
					if (result != 'error'){
						$("#smallpop-apprequest").remove();
						$("li#menu_pop_game").find('div.menu-pop-box:first').append(result);
						$('.timeago').timeago();
					}
					else{
						alert('Có lỗi trong quá trình xử lý. Các bạn vui lòng thử lại sau !');
					}
					//jQuery('.btn_add_friend').prepend(result);
					
				}
			);
			loading.addClass('hide');
		}
	});
});

// tuantm
function ntfn_do_accept_friend(target){
	var $ = jQuery;
	target = $(target);

	var userinfo	= target.attr('id').split(":");
	var userid 		= userinfo[0];
	var username 	= userinfo[1];

	$.post(
	BaseURL+"member.php",
	{
		'action'	: 'do_accept_friend',
		'ctrl'		: 'userrelation',
		'userid'	: userid,
		'username'	: username
	},
	function(result){
		if (result == 'done'){
			$(target).parents('li:first').fadeOut('slow');
		}
		else{
			alert('Có lỗi trong quá trình xử lý. Các bạn vui lòng thử lại sau.');
		}
	}
	);
}

/*function do_accept_friend_notification(target){
var $ = jQuery;
target = $(target);

var id = target.attr('id');
//alert(id);
var temp = id.split(":");
var userid = temp[0];
var username = temp[1];

$.post(BaseURL+"member.php",{
'action':'do_accept_friend',
'ctrl':'userrelation',
'userid':userid,
'username':username
},function(result)
{
if (result == 'done'){
target.removeAttr('onclick').html('Đã là bạn');
target.parents('div:first').find("span.btn_reject_friend").remove();
}
else{
alert('Có lỗi trong quá trình xử lý. Các bạn vui lòng thử lại sau.');
}
//jQuery('.btn_add_friend').prepend(result);
}
);
}*/

// tuantm
function ntfn_do_reject_friend(target){
	var $ = jQuery;
	target = $(target);

	var userinfo	= target.attr('id').split(":");
	var userid 		= userinfo[0];
	var username 	= userinfo[1];

	$.post(
	BaseURL+"member.php",
	{
		'action'	: 'do_reject_friend',
		'ctrl'		: 'userrelation',
		'userid'	: userid,
		'username'	: username
	},
	function(result){
		if (result == 'done'){
			$(target).parents('li:first').fadeOut('slow');
		}
		else{
			alert('Có lỗi trong quá trình xử lý. Các bạn vui lòng thử lại sau.');
		}
	}
	);
}

function process_apprequest(e){
	e.preventDefault();
	var $ = jQuery;
	target = $(e.target);

	var ntfnInfo = target.parents('p:first').attr('id').split('-');
	var request_id = ntfnInfo[1];
	var ntfn_id = ntfnInfo[2];

	var redirectURL = target.attr('href');

	$.post(
		BaseURL + 'member.php',
		{
			ctrl		: 'apprequest',
			action		: 'process_request',
			request_id	: request_id,
			ntfn_id		: ntfn_id
		},
		function(result){
			if (result == 'success'){
				target.parents('li:first').fadeOut('fast');
				if (redirectURL.length > 0){
					window.location.href = redirectURL;
				}
			}
		}
	);
}