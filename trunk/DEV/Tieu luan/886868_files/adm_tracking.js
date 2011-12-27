var _ADMFlashDetect = new function(){
	var self = this;
	self.installed = false;
	self.raw = "";
	self.major = -1;
	self.minor = -1;
	self.revision = -1;
	self.revisionStr = "";
	var activeXDetectRules = [
		{
			"name":"ShockwaveFlash.ShockwaveFlash.7",
			"version":function(obj){
				return getActiveXVersion(obj);
			}
		},
		{
			"name":"ShockwaveFlash.ShockwaveFlash.6",
			"version":function(obj){
				var version = "6.0.r21";
				try{
					obj.AllowScriptAccess = "always";
					version = getActiveXVersion(obj);
				}catch(err){}
				return version;
			}
		},
		{
			"name":"ShockwaveFlash.ShockwaveFlash",
			"version":function(obj){
				return getActiveXVersion(obj);
			}
		}
	];
   
	var getActiveXVersion = function(activeXObj){
		var version = -1;
		try{
			version = activeXObj.GetVariable("$version");
		}catch(err){}
		return version;
	};
   
	var getActiveXObject = function(name){
		var obj = -1;
		try{
			obj = new ActiveXObject(name);
		}catch(err){
			obj = {activeXError:true};
		}
		return obj;
	};
	
	var parseActiveXVersion = function(str){
		var versionArray = str.split(",");//replace with regex
		return {
			"raw":str,
			"major":parseInt(versionArray[0].split(" ")[1], 10),
			"minor":parseInt(versionArray[1], 10),
			"revision":parseInt(versionArray[2], 10),
			"revisionStr":versionArray[2]
		};
	};
   
	var parseStandardVersion = function(str){
		var descParts = str.split(/ +/);
		var majorMinor = descParts[2].split(/\./);
		var revisionStr = descParts[3];
		return {
			"raw":str,
			"major":parseInt(majorMinor[0], 10),
			"minor":parseInt(majorMinor[1], 10), 
			"revisionStr":revisionStr,
			"revision":parseRevisionStrToInt(revisionStr)
		};
	};
  
	var parseRevisionStrToInt = function(str){
		return parseInt(str.replace(/[a-zA-Z]/g, ""), 10) || self.revision;
	};
  
	self.majorAtLeast = function(version){
		return self.major >= version;
	};
 
	self.minorAtLeast = function(version){
		return self.minor >= version;
	};
   
	self.revisionAtLeast = function(version){
		return self.revision >= version;
	};
	
	self.versionAtLeast = function(major){
		var properties = [self.major, self.minor, self.revision];
		var len = Math.min(properties.length, arguments.length);
		for(i=0; i<len; i++){
			if(properties[i]>=arguments[i]){
				if(i+1<len && properties[i]==arguments[i]){
					continue;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}
	};
  
	self._ADMFlashDetect = function(){
		if(navigator.plugins && navigator.plugins.length>0){
			var type = 'application/x-shockwave-flash';
			var mimeTypes = navigator.mimeTypes;
			if(mimeTypes && mimeTypes[type] && mimeTypes[type].enabledPlugin && mimeTypes[type].enabledPlugin.description){
				var version = mimeTypes[type].enabledPlugin.description;
				var versionObj = parseStandardVersion(version);
				self.raw = versionObj.raw;
				self.major = versionObj.major;
				self.minor = versionObj.minor; 
				self.revisionStr = versionObj.revisionStr;
				self.revision = versionObj.revision;
				self.installed = true;
			}
		}else if(navigator.appVersion.indexOf("Mac")==-1 && window.execScript){
			var version = -1;
			for(var i=0; i<activeXDetectRules.length && version==-1; i++){
				var obj = getActiveXObject(activeXDetectRules[i].name);
				if(!obj.activeXError){
					self.installed = true;
					version = activeXDetectRules[i].version(obj);
					if(version!=-1){
						var versionObj = parseActiveXVersion(version);
						self.raw = versionObj.raw.replace('Shockwave Flash ','');
						self.major = versionObj.major;
						self.minor = versionObj.minor; 
						self.revision = versionObj.revision;
						self.revisionStr = versionObj.revisionStr;
					}
				}
			}
		}
	}();
}
var paramBrowser={
	screen_Resolution:screen.width.toString()+screen.height.toString(),
	screen_Color:screen.colorDepth,
	hostname:document.domain.replace('www.',''),
	cookieEnabled:(navigator.cookieEnabled)?1:0,
	javaEnabled:(navigator.javaEnabled())?1:0,
	referrer:document.referrer,
	url:function(){
		var strlocation=top.location.toString();
		var hostname=this.hostname;
		if(hostname==''){
			return strlocation;
		}
		else{
			return strlocation.substring(strlocation.indexOf(hostname)+hostname.length,strlocation.length);
		}
	},
	flashVersion:function(){
		var version=_ADMFlashDetect.major+'.'+_ADMFlashDetect.minor+'.'+_ADMFlashDetect.revisionStr;
		return version;
	 },
	zenURL:{
		encode:function(url){
			var hostname=document.domain;
			if(hostname.indexOf('muachung.vn')!=-1){
				if(url.indexOf('http://')==-1){
					var mc_city=ADS_CHECKER.getCookie('mc_city');
					var f=["/ha-noi","/tp-ho-chi-minh","/da-nang","/nha-trang","/vung-tau","/hai-phong"];
					var r=["/Ha-Noi","/TP-HCM","/Da-Nang","/Khanh-Hoa","/Ba-Ria-Vung-Tau","/Hai-Phong"];
					for(var i=0;i<f.length;i++){
						if(url.indexOf(f[i])==0){
							url=url.replace(f[i],r[i]);
							return encodeURIComponent(url);
						}
					}
					var k={"22":"/Ha-Noi","29":"/TP-HCM","15":"/Da-Nang","68":"/Khanh-Hoa","26":"/Hai-Phong","67":"/Ba-Ria-Vung-Tau"};
					if(mc_city!='' && (typeof(k[mc_city])!='undefined')){
						url=k[mc_city]+url;
	
					}
				}
				return encodeURIComponent(url);
				
			}
			else{
				return encodeURIComponent(url);
			}
		},
		decode:function(url){
			var encoded=url;
			return decodeURIComponent(encoded.replace(/\+/g,  " "));
		}
	}
}
var _ADMBrowser = {
	  Version: function() {
		var version = 999; // we assume a sane browser
		if (navigator.appVersion.indexOf("MSIE") != -1)
		  // bah, IE again, lets downgrade version number
		  version = parseFloat(navigator.appVersion.split("MSIE")[1]);
		return version;
	  }
}
var _admBrV=_ADMBrowser.Version();


function getJSON(){
	var rand = Math.random();
	rand = Math.floor(rand*10);
	rand = (rand == 0) ? 1: rand;
    var url = "http://logging.admicro.vn/_tracking.gif?";
	url += "fl="+paramBrowser.flashVersion();
	url += "&je="+paramBrowser.javaEnabled;
	url += "&sr="+paramBrowser.screen_Resolution;
	url += "&sc="+paramBrowser.screen_Color;
	url += "&hn="+paramBrowser.hostname;
	url += "&p="+paramBrowser.zenURL.encode(paramBrowser.url());	
	url += "&r="+((paramBrowser.referrer=='')?paramBrowser.referrer:paramBrowser.zenURL.encode(paramBrowser.referrer));	
	var g=0;
	
	if(paramBrowser.hostname.indexOf('enbac.com')!=-1){
		g=(ADS_CHECKER.getCookie('province_id')!='')?ADS_CHECKER.getCookie('province_id'):0;
	}
	if(paramBrowser.hostname.indexOf('rongbay.com')!=-1){
		g=(ADS_CHECKER.getCookie('cityid')!='')?ADS_CHECKER.getCookie('cityid'):0;
	}
	
	url += "&g="+g;
	
	
	
	var a = document.createElement("script");
	a.type = "text/javascript";
	a.async = true;
	a.src = url;
	var c = document.getElementsByTagName("script")[0];
	c.parentNode.insertBefore(a, c);
	
	
    //headElement.removeChild(scriptTag); 
}

var _admTrackingTime=0;
function checkgetJSON(){
	
	if((window.document.readyState=='complete')||(typeof(window.document.readyState)=='undefined')){
		getJSON();
	}
	else{
		_admTrackingTime++;
		
		if(_admTrackingTime==3){getJSON();return false;}
		
		setTimeout("checkgetJSON()",1000);
		
	}
}
checkgetJSON();

//getJSON();