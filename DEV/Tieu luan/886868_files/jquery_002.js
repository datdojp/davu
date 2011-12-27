/*!
 * Tiny Scrollbar 1.65
 * http://www.baijs.nl/tinyscrollbar/
 *
 * Copyright 2010, Maarten Baijs
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.opensource.org/licenses/gpl-2.0.php
 *
 * Date: 10 / 05 / 2011
 * Depends on library: jQuery
 * 
 */

(function($){
	$.tiny = $.tiny || { };
	
	$.tiny.scrollbar = {
		options: {	
			axis: 'y', // vertical or horizontal scrollbar? ( x || y ).
			wheel: 30,  //how many pixels must the mouswheel scroll at a time.
			scroll: true, //enable or disable the mousewheel;
			size: 'auto', //set the size of the scrollbar to auto or a fixed number.
			sizethumb: 'auto' //set the size of the thumb to auto or a fixed number.
		}
	};	
	
	$.fn.tinyscrollbar = function(options) { 
		var options = $.extend({}, $.tiny.scrollbar.options, options); 		
		this.each(function(){ $(this).data('tsb', new Scrollbar($(this), options)); });
		return this;
	};
	$.fn.tinyscrollbar_update = function(sScroll) { return $(this).data('tsb').update(sScroll); };
    $.fn.tinyscrollbar_scrollTop = function() { return $(this).data('tsb').cuteScrollTop(); };

	function Scrollbar(root, options){
		var oSelf = this;
		var oWrapper = root;
		var oViewport = { obj: $('.ticker-content-box', root) };
		var oContent = { obj: $('.ticker-content', root) };
		var oScrollbar = { obj: $('.ticker-scrollbar', root) };
        var oScrolltop = {obj:$('.ticker-top',root)};
		var oTrack = { obj: $('.ticker-track', oScrollbar.obj) };
		var oThumb = { obj: $('.ticker-thumb', oScrollbar.obj) };
		var sAxis = options.axis == 'x', sDirection = sAxis ? 'left' : 'top', sSize = sAxis ? 'Width' : 'Height';
		var iScroll, iPosition = { start: 0, now: 0 }, iMouse = {};
        //cute edit
        var holdMouse = false;
        var pMax = 0;
        var cMax = 0;
        var isUpdate = false;
         //if small item
        if(oViewport.obj.height() > oContent.obj.height() - 20) {
            $('div.ticker-update',oContent.obj).addClass('scroll-disable');
        }

		function initialize() {
			oSelf.update();
			setEvents();
			return oSelf;
		}
		this.update = function(sScroll){
            /*loadding
            var loading_id = $('div.ticker-update',oContent.obj);
            var loading_html = loading_id.clone();
            loading_id.remove();
            oContent.obj.append(loading_html);*/
            //update scroll
			oViewport[options.axis] = oViewport.obj[0]['offset'+ sSize];
			oContent[options.axis] = oContent.obj[0]['scroll'+ sSize];
			oContent.ratio = oViewport[options.axis] / oContent[options.axis];
			oScrollbar.obj.toggleClass('scroll-disable', oContent.ratio >= 1);
            
			oTrack[options.axis] = options.size == 'auto' ? oViewport[options.axis] : options.size;
			oThumb[options.axis] = Math.min(oTrack[options.axis], Math.max(0, ( options.sizethumb == 'auto' ? (oTrack[options.axis] * oContent.ratio) : options.sizethumb )));
			oScrollbar.ratio = options.sizethumb == 'auto' ? (oContent[options.axis] / oTrack[options.axis]) : (oContent[options.axis] - oViewport[options.axis]) / (oTrack[options.axis] - oThumb[options.axis]);
            iScroll = (sScroll == 'relative' && oContent.ratio <= 1) ? Math.min((oContent[options.axis] - oViewport[options.axis]), Math.max(0, iScroll)) : 0;
			iScroll = (sScroll == 'bottom' && oContent.ratio <= 1) ? (oContent[options.axis] - oViewport[options.axis]) : isNaN(parseInt(sScroll)) ? iScroll : parseInt(sScroll);
            cMax = oContent[options.axis] - oViewport[options.axis];
            pMax = cMax / oScrollbar.ratio;//max postion
            setSize();
		};
        //scrolltop for public call
        this.cuteScrollTop = function(){callScrolltop()};

        //Method private
		function setSize(){
            oThumb.obj.css(sDirection, iScroll / oScrollbar.ratio);
            //animateScroll(oThumb.obj,sDirection,iScroll / oScrollbar.ratio,100);
			//oContent.obj.css(sDirection, -iScroll);
            animateScroll(oContent.obj,sDirection,-iScroll,600);
			iMouse['start'] = oThumb.obj.offset()[sDirection];
			var sCssSize = sSize.toLowerCase(); 
			oScrollbar.obj.css(sCssSize, oTrack[options.axis]);
			oTrack.obj.css(sCssSize, oTrack[options.axis]);
			oThumb.obj.css(sCssSize, oThumb[options.axis]);
            //open new update
            isUpdate = false;
		}
        
		function setEvents(){
			oThumb.obj.bind('mousedown', start);
			oThumb.obj[0].ontouchstart = function(oEvent){
				oEvent.preventDefault();
				oThumb.obj.unbind('mousedown');
				start(oEvent.touches[0]);
				return false;
			};	
			oTrack.obj.bind('mouseup', drag);
			if(options.scroll && this.addEventListener){
				oWrapper[0].addEventListener('DOMMouseScroll', wheel, false);
				oWrapper[0].addEventListener('mousewheel', wheel, false );
			}
			else if(options.scroll){oWrapper[0].onmousewheel = wheel;}
		}
        
		function start(oEvent){
			iMouse.start = sAxis ? oEvent.pageX : oEvent.pageY;
			var oThumbDir = parseInt(oThumb.obj.css(sDirection));
			iPosition.start = oThumbDir == 'auto' ? 0 : oThumbDir;
			$(document).bind('mousemove', drag);
			document.ontouchmove = function(oEvent){
				$(document).unbind('mousemove');
				drag(oEvent.touches[0]);
			};
			$(document).bind('mouseup', end);
			oThumb.obj.bind('mouseup', end);
			oThumb.obj[0].ontouchend = document.ontouchend = function(oEvent){
				$(document).unbind('mouseup');
				oThumb.obj.unbind('mouseup');
				end(oEvent.touches[0]);
			};
            //for active bar
            holdMouse = true;
			return false;
		}
		function wheel(oEvent){
			if(!(oContent.ratio >= 1)){
				oEvent = $.event.fix(oEvent || window.event);
				var iDelta = oEvent.wheelDelta ? oEvent.wheelDelta/120 : -oEvent.detail/3;
				iScroll -= iDelta * options.wheel;
				iScroll = Math.min((oContent[options.axis] - oViewport[options.axis]), Math.max(0, iScroll));
                oScrolltop.obj.toggleClass('scroll-disable', iScroll <= 30);
                oThumb.obj.css(sDirection, iScroll / oScrollbar.ratio);
                oContent.obj.css(sDirection,-iScroll);
                //animateScroll(oContent.obj,sDirection,-iScroll,0);
				oEvent.preventDefault();
                //if(iScroll >= cMax - 18){callUpdate();}
			}
		}
		function end(oEvent){
			$(document).unbind('mousemove', drag);
			$(document).unbind('mouseup', end);
			oThumb.obj.unbind('mouseup', end);
			document.ontouchmove = oThumb.obj[0].ontouchend = document.ontouchend = null;
			//for active bar
            holdMouse = false;
            if(!root.hasClass('hover')){root.removeClass('active');}
            return false;
		}
		function drag(oEvent){
			if(!(oContent.ratio >= 1)){
				iPosition.now = Math.min((oTrack[options.axis] - oThumb[options.axis]), Math.max(0, (iPosition.start + ((sAxis ? oEvent.pageX : oEvent.pageY) - iMouse.start))));
				iScroll = iPosition.now * oScrollbar.ratio;
				oContent.obj.css(sDirection,-iScroll);
                //animateScroll(oContent.obj,sDirection,-iScroll,0);
				oThumb.obj.css(sDirection, iPosition.now);
                oScrolltop.obj.toggleClass('scroll-disable', iScroll <= 30);
                //if(iPosition.now >= pMax - 18){callUpdate()}
                //animateScroll(oThumb.obj,sDirection,iPosition.now,300);
			}
			return false;
		}
        function callUpdate(){
            if(!isUpdate) {
                isUpdate=true;
                $.cuteUpdate();
                console.log('sexy cute');
            }
        }

        //Method default scrolltop
        oScrolltop.obj.bind('click',function(){
            callScrolltop();
        });

        root.hover(
            function(){
                $(this).addClass('active hover');
            },function(){
                if(!holdMouse){$(this).removeClass('active hover')}
                else {$(this).removeClass('hover')}
            }
        );
        //Scroll top
        function callScrolltop(){
            var sDirec = 'top';
            iScroll = 0;
            animateScroll(oThumb.obj,sDirec,0,300);
            animateScroll(oContent.obj,sDirec,iScroll,600);
            oScrolltop.obj.toggleClass('scroll-disable', iScroll <= 30);
        }
        //Animate for scroll 
        function animateScroll (obj,sDirec,pos,dura){
            if(sDirec == 'top'){obj.stop().animate({top:pos},{queue:false, duration:dura, easing:'easeOutQuint'})}
            if(sDirec == 'left'){obj.stop().animate({left:pos},{queue:false, duration:dura, easing: 'easeOutQuint'})}
        }
        
	    return initialize();
	}
})(jQuery);