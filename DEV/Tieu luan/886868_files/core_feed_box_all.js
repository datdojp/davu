/* by tinhdangquang@vccorp.vn */

var root = 'http://sohagame.vcmedia.vn/embedbox/';
var tags = new Array();
tags[0] = 'sohagame:feedboxall';
var strs = new Array();
strs[0] = 'sohagameplugins';
strs[1] = 'feedboxall';

if (!window.mp) window.mp = {};
(function () {
	var d = window,
		h = mp,
		i = document;		
	try {
		//ce create element		
		h.ce = function (tagName){
			return document.createElement(tagName);
		}
		
		//ac append child
		h.ac = function (objChild, objParent){
			objParent.appendChild(objChild);
		}
		
		//sa setattribute
		h.sa = function (obj, att, vl){
			obj.setAttribute(att, vl);
		}
		
		//ga getattribute
		h.ga = function (obj, att){						
			return obj.getAttribute(att);
		}
		
		//go get element
		h.ge = function (byID){
			return document.getElementById(byID);
		}
		
		//gebtn get element by tag name
		h.gebtn = function (byTag){
			return  document.getElementsByTagName(byTag);
		}

	} catch (C) {
		h.ml(C, false, {
			cause: strs[0]
		})
	};	
})();




if (!window.feedboxall) window.feedboxall = {};
(function () {
	var l = window,
		m = feedboxall,
		n = document;			
	try {
		m.initial = function (){										
			var objFeed = mp.gebtn(tags[0]);												var c_service = mp.ga(objFeed[0], 'service');
			var att = 'channel='+c_service;					
			box_width = mp.ga(objFeed[0], 'width');
			box_height = mp.ga(objFeed[0], 'height');
							
			var oi = mp.ce('iframe');	
			oi.id = 'fmWidget';
			mp.sa(oi, 'scrolling', 'no');
			objDate = new Date();
			html_v = objDate.getTime();			
			mp.sa(oi, 'src', root+'global_feed_'+c_service+'.html?v='+html_v);
			oi.style.height=box_height;
			oi.style.width=box_width;
			oi.style.border='0';
			oi.frameBorder = 0;
			mp.sa(oi, 'style','border:0;height:'+box_height+';width:'+box_width);
			mp.sa(oi, 'frameborder', '0');
			var objForAppend = mp.ge(strs[1]);						
			mp.sa(objForAppend, 'style','width:'+box_width+';height:'+box_height+';overflow:none');
			objForAppend.style.height = box_height;
			objForAppend.style.width  = box_width;
			mp.ac(oi,objForAppend);								
		}				
	} catch (C) {
		m.ml(C, false, {
			cause: strs[0]
		})
	};	
	feedboxall.initial();	
})();


