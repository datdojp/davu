
var imgHost='http://admicro2.vcmedia.vn/adt/cpc/';if(typeof(_admBrV)=='undefined'){var _ADMBrowser={Version:function(){var version=999;if(navigator.appVersion.indexOf("MSIE")!=-1)version=parseFloat(navigator.appVersion.split("MSIE")[1]);return version;}}
var _admBrV=_ADMBrowser.Version();}
function AdmZones(){this.rand=Math.random();this.fl=Math.floor;this.domain='http://'+document.domain+'/';this.channel=document.location.href;this.rand_limit=2;this.zoneid=-1;var campRunned=[];this.ClientLo=0;var aThis=this;this.bannerRun=[];this.randNum=function(){return this.fl(this.rand*11)+1;}
this.clLocat=function(name){if(typeof(this.getCookie(name))!='undefine'||this.getCookie(name)!=''){return this.ClientLo=this.getCookie(name);}
return 0;}
if(!Array.prototype.indexOf){Array.prototype.indexOf=function(elt){var len=this.length;var from=Number(arguments[1])||0;from=(from<0)?Math.ceil(from):Math.floor(from);if(from<0)from+=len;for(;from<len;from++){if(from in this&&this[from]===elt)return from;}
return-1;};}
this.campWillRun=function(a){var d=a.length,b=[],cookCpRunned=this.cookCpRunned(),cookCRLen=cookCpRunned.length,c=this.explode(',',cookCpRunned),cLen=c.length,clientLo=this.clLocat('__R'),clientLoC=this.clLocat('__RC');cookCpRunned=cookCpRunned.substr(0,cookCRLen-1);if(clientLo=='0'||clientLoC=='0'||clientLo==''){clientLo=0;var aL=[];for(var i=0;i<d;i++){var t=','+a[i]['l'].toString()+',';if((t.indexOf(',1,2,3,')!=-1)||(t==',,')){aL.push(a[i]);}}
return aL;}
var clientLo=','+clientLo+',';var clientLoC=','+clientLoC+',';if(clientLo!=0||clientLoC!=0){var aL=[];for(var i=0;i<d;i++){var t=','+a[i]['l'].toString()+',';if((t.indexOf(clientLo)!=-1)||(t.indexOf(clientLoC)!=-1)){aL.push(a[i]);}}
b=aL;allIl=b.length;}
var d=b.length;if(d<=this.rand_limit){b=a;allIl=b.length;var aL=[];for(var i=0;i<allIl;i++){var t=','+a[i]['l'].toString()+',';if((t.indexOf(clientLo)!=-1)||(t.indexOf(clientLoC)!=-1)){aL.push(a[i]);}}
b=aL,allIl=b.length;}
if(cookCpRunned==''||cookCpRunned==null||typeof cookCpRunned=='undefined'){b=a
allIl=b.length;var aL=[];for(var i=0;i<allIl;i++){var t=','+a[i]['l'].toString()+',';if((t.indexOf(clientLo)!=-1)||(t.indexOf(clientLoC)!=-1)){aL.push(a[i]);}}
b=aL,allIl=b.length;}else{for(var i=0;i<d;i++){if(typeof(a[i])!='undefined'){if(cookCpRunned.indexOf('_'+a[i]['bid'])===-1&&b.indexOf(a[i])===-1){var t=','+a[i]['l'].toString()+',';if((t.indexOf(clientLo)!=-1)||(t.indexOf(clientLoC)!=-1)){aL.push(a[i]);}}}}
if(b==''&&cookCpRunned!=''){this.removeZoneCook(cookCpRunned);}}
if(window["ADS_CHECKER"]!=undefined&&window["ADS_CHECKER"]!="undefined"){var m=ADS_CHECKER.array_diff(a,b);if(b.length<this.rand_limit&&m.length>0){var k=[];for(var i=0;i<(m.length);i++){var t=','+m[i]['la'].toString()+',';if(((t.indexOf(clientLo)!=-1)||(t.indexOf(clientLoC)!=-1))){k.push(m[i]);}}
var len=((this.rand_limit-b.length)>k.length)?k.length:(this.rand_limit-b.length);k=this.Shuffle(k);for(var i=0;i<len;i++){b.push(k[i]);}}}
bL=b.length;if((bL+this.rand_limit)>allIl){this.setCookie('cpcSelfServ','',1,1);cookCpRunned=this.cookCpRunned(),cookCRLen=cookCpRunned.length;}
return b;}
this.removeZoneCook=function(curCookRunned){var zoneCook=this.getCookie('cpcSelfServ'),arrCookCpRunned=curCookRunned.split(','),arrCookCpRunLen=arrCookCpRunned.length-1;for(var i=0;i<arrCookCpRunLen;i++){zoneCook=zoneCook.replace(arrCookCpRunned[i]+',','');}
this.setCookie('cpcSelfServ',zoneCook,1,1);}
this.cookCpRunned=function(){var zoneCook=this.getCookie('cpcSelfServ'),strCookCpRunned='';if((zoneCook!=''&&zoneCook.length>1)||typeof(zoneCook)!='undefined'){var arrzoneCook=zoneCook.toString().split(',');for(var i=0;i<arrzoneCook.length;i++){if(arrzoneCook[i].indexOf(this.zoneid+'_')!=-1){strCookCpRunned+=arrzoneCook[i]+',';}}
return strCookCpRunned;}else{return'';}}
this.explode=function(delimiter,string,limit){var emptyArray={0:''};if(arguments.length<2||typeof arguments[0]=='undefined'||typeof arguments[1]=='undefined'){return null;}
if(delimiter===''||delimiter===false||delimiter===null){return false;}
if(typeof delimiter=='function'||typeof delimiter=='object'||typeof string=='function'||typeof string=='object'){return emptyArray;}
if(delimiter===true){delimiter='1';}
if(!limit){return string.toString().split(delimiter.toString());}else{var splitted=string.toString().split(delimiter.toString()),partA=splitted.splice(0,limit-1),partB=splitted.join(delimiter.toString());if(typeof(partB)!='undefined'||partB!=''||partB>0){partA.push(partB);}
return partA;}}
this.setCookie=function(c_name,value,expiredays,reset){var exdate=new Date();exdate.setDate(exdate.getDate()+expiredays);if(reset==1){document.cookie=c_name+"="+escape(value)+((expiredays==null)?"":";expires="+exdate.toUTCString())+";path=/";}else{var curCook=this.getCookie('cpcSelfServ');if(curCook.search(value)<0||curCook==''||curCook==null){document.cookie=c_name+"="+escape(curCook+value)+((expiredays==null)?"":";expires="+exdate.toUTCString())+";path=/";}}}
this.isArray=function(obj){return obj.constructor==Array;}
this.getCookie=function(c_name){if(document.cookie.length>0){c_start=document.cookie.indexOf(c_name+"=");if(c_start!=-1){c_start=c_start+c_name.length+1;c_end=document.cookie.indexOf(";",c_start);if(c_end==-1)c_end=document.cookie.length;return unescape(document.cookie.substring(c_start,c_end));}}
return"";}
this.Shuffle=function(v){var vlen=v.length;for(var j,x,i=vlen;i;j=parseInt(Math.random()*i),x=v[--i],v[i]=v[j],v[j]=x);return v;}
this.toArray=function(o){var a=[];for(x in o){a.push(o[x]);}
return a;}
this.CreateStyleSheet=function(){var css='#ssvzone_1626{-moz-box-shadow: 0px 0px 3px #cecece;-webkit-box-shadow: 0px 0px 3px #cecece;box-shadow: 0px 0px3px#cecece;height:110px;font-family: Arial;font-size:12px;color: #686868;border: 1px solid #cccccc;background:#fff;width:840px;overflow: hidden;clear: both;}  #ssvzone_1626 .adv_item{padding-left:100px;} #ssvzone_1626 .ssvzone_1626border{background:#cecece;width:1px;height:110px; float:left;} #ssvzone_1626 .specLeftContent { float:left;width: 112px;height: 110px;background: transparent url('+imgHost+'logossv.gif) top left no-repeat; margin-top: 5px; margin-left: -3px; clear: both;} #ssvzone_1626 .ssvzone_1626_items {height:110px;float:left;padding:10px;width:345px;} #ssvzone_1626 .ssvzone_1626_items .admicroBoxTitle{float:left;width:83px;padding-left:20px;font-size:11px;font-weight:bold;padding-right: 10px;text-align: left;} #ssvzone_1626 .ssvzone_1626_items .admicroBoxTitle a, #ssvzone_1626 .ssvzone_1626_items .admicroBoxTitle a:link,#ssvzone_1626 .ssvzone_1626_items .admicroBoxTitle a:active {text-decoration:none;float:left; width:88px; color:#333;font-size:11px;font-weight:bold;}#ssvzone_1626 .ssvzone_1626_items  .ssvzimage img{float:left; width:90px; border:none;height:90px;font-size:11px; font-weight:bold;}#ssvzone_1626 .ssvzone_1626_items  .admicroBoxMore{text-align:left;float:left;width:120px;margin-left:20px;font-size:11px;font-weight:normal; font-family:Tahoma, Geneva, sans-serif; } #ssvzone_1626 .ssvzone_1626_items  .admicroBoxMore a,  #ssvzone_1626 .ssvzone_1626_items .admicroBoxMore a:link, #ssvzone_1626 .ssvzone_1626_items  .admicroBoxMore a:active,  #ssvzone_1626 .ssvzone_1626_items  .admicroBoxMore a:link{float:left;width:120px; color:#686868; text-decoration:none;}.dmds{font-size:11px;font-weight:normal;color:#666; } #ssvzone_1626 .ssvzone_1626_items .dmds a, #ssvzone_1626 .ssvzone_1626_items  .dmds a:link,  #ssvzone_1626 .ssvzone_1626_items .dmds a:active, #ssvzone_1626 .ssvzone_1626_items .dmds a:link {font-size:11px;font-weight:normal;color:#666;}',doc=document,style=doc.createElement('style');style.type='text/css';var head=doc.getElementsByTagName('head')[0];head.appendChild(style);if(style.styleSheet)style.styleSheet.cssText=css;else style.appendChild(doc.createTextNode(css));}
this.InitScript=function(){this.CreateStyleSheet();}
this.escapeHtml=function(str){var stra=str.replace(/<(span|strong|b)[^>]*>(.*?)<\/(span|strong|b)>/g,'$2');stra=stra.replace(/\"/g,'&quot;');return stra;}
this.PArray=function(a,b,c){var x=-1;for(i=0;i<c;i++){if(b[i]['bid']==a){x=i;}}
return x;}
this.trackBanner=function(i,a,b){var d=a;var cAllI=b.length,bPost=-1;bPost=this.PArray(i,b,cAllI);if(bPost<0){d=a;}else{var cA=a.length,biPos=-1;biPos=this.PArray(i,a,cA);if(biPos>=0){d=a;}else{a[0]=b[bPost];d=a;}}
return d;}
this.DrawBanner=function(){var json=aThis.data,output='',allItems=[];this.zoneid=json['ZONEID'];var docBizone=document.getElementById('ssvzone_1626_items');this.camplist=this.campWillRun(this.toArray(json['DATA']));var cookCpRunned=this.cookCpRunned(),campLen=this.camplist.length,banLog='';trackI='';if(window["ADS_CHECKER"]!=undefined&&window["ADS_CHECKER"]!="undefined"){trackI=ADS_CHECKER.getQuery('i');}
if(campLen<=this.rand_limit){var Items=this.Shuffle(this.camplist),iLen=Items.length,lastI=[],k=0;for(var j=0;j<iLen;j++){if(lastI.length==0){lastI.push(Items[j]);k=k+1;}else{if(lastI.indexOf(Items[j])==-1){lastI.push(Items[j]);k=k+1;}}
if(k==this.rand_limit)break;}
Items=lastI;Items=this.trackBanner(trackI,Items,this.toArray(json['DATA']));Items=this.Shuffle(Items);var addI=this.rand_limit-Items.length,j=0;this.camplist=this.Shuffle(this.camplist);var camplistadd=this.camplist;if(typeof(_ssv_default)!=undefined&&typeof(_ssv_default)!='undefined'){_ssv_default=this.Shuffle(_ssv_default);if(_ssv_default.length>0){camplistadd=_ssv_default;}}
campLen=camplistadd.length;for(var i=0;i<addI;i++){if(j<campLen){Items.push(camplistadd[j]);}else{camplistadd=this.Shuffle(camplistadd);j=0;Items.push(camplistadd[j]);}
j++;}
if(campLen==0){var ssvzone_1626=document.getElementById('ssvzone_1626');ssvzone_1626.style.display='none';return false;}}else if(campLen>this.rand_limit){var Items=this.Shuffle(this.camplist),iLen=Items.length,lastI=[],k=0;for(var j=0;j<iLen;j++){if(lastI.length==0){lastI.push(Items[j]);k=k+1;}else{if(lastI.indexOf(Items[j])==-1){lastI.push(Items[j]);k=k+1;}}
if(k==this.rand_limit)break;}
Items=lastI;Items=this.trackBanner(trackI,Items,this.toArray(json['DATA']));Items=this.Shuffle(Items);if(Items.length<this.rand_limit){var slotI=this.rand_limit-Items.length;for(var i=0;i<=slotI;i++){Items.push(Items[i]);}}}
var ri=this.randNum(),rdca=(this.rand*9),curDom=document.domain;for(var j=0;j<this.rand_limit;j++){if(iLen>this.rand_limit){this.setCookie('cpcSelfServ',this.zoneid+'_'+Items[j]['bid']+',',1,'');}
if(this.bannerRun.indexOf(Items[j]['bid'])==-1){this.bannerRun.push(Items[j]['bid']);banLog=banLog+Items[j]['cid']+'|'+Items[j]['bid']+',';}
var logurl='http://logging.admicro.vn/_adc.html?adm_domain='+escape(this.channel)+'&adm_campaign='+Items[j]['cid']+'&adm_aditem='+Items[j]['bid']+'&adm_zoneid='+this.zoneid+'&adm_channelid=-1&adm_rehttp='+escape(Items[j]['link'])+'&adm_random='+(this.rand*9)+'';var logurl1='http://logging.admicro.vn/_adc.html?adm_domain='+escape(this.channel)+'&adm_campaign='+Items[j]['cid']+'&adm_aditem='+Items[j]['bid']+'&adm_zoneid='+this.zoneid+'&adm_channelid=-1&adm_rehttp='+escape(Items[j]['link']+'?ref='+curDom+'&bi='+Items[j]['bid']+'&ci='+Items[j]['cid'])+'&adm_random='+rdca+'';lineBorder='';var anC='';if(Items[j]['bid']){anC='<div id="'+this.zoneid+'_'+Items[j]['bid']+'" style="height:0px;width:0px;overflow:hidden;"><span></span></div>';}
if(Items[j]['descr'].indexOf('muachung.vn')!=-1){logurl=logurl1;}
var title=this.escapeHtml(Items[j]['title']);if(window["ADS_CHECKER"]!=undefined&&window["ADS_CHECKER"]!="undefined"){var qr=ADS_CHECKER.getQuery('admdebug');if(qr!=''){title='B='+Items[j]['bid']+', L='+Items[j]['l']+', LA='+Items[j]['la']+', R='+ADS_CHECKER.getCookie('__R')+', RC='+ADS_CHECKER.getCookie('__RC');}}
output+='<div class="ssvzone_1626_items">'+anC+'<div class="admicroBoxTitle"><a href="'+logurl+'" target="_blank" title="'+title+'">'+Items[j]['title']+'</a><div class="dmds"><a href="'+logurl+'" target="_blank" title="'+this.escapeHtml(Items[j]['descr'])+'">'+Items[j]['descr']+'</a></div></div><div class="ssvzimage"><a href="'+logurl+'" target="_blank" title="'+title+'"><img align="left" src="'+imgHost+'zoneimages/spacer.gif" vspace="0" hspace="0" border="0" alt="'+title+'" style=" background:url(\''+Items[j]['image']+'\') no-repeat center center" /></a></div> <div class="admicroBoxMore"><a href="'+logurl+'" target="_blank" title="'+this.escapeHtml(Items[j]['content'])+'"> '+Items[j]['content']+'</a></div></div>'+lineBorder;if(j<(this.rand_limit-1)){output+='<div  class ="ssvzone_1626border"><span></span></div>';}}
if(docBizone){docBizone.innerHTML=output;}
if(document.images&&banLog!=''){var img=new Image();img.src='http://logging.admicro.vn/_zc.1.gif?_domain='+escape(this.channel)+'&_ci='+banLog.slice(0,-1)+'&_zid='+this.zoneid;}}
this.OnLoaded=function(data,methodName){aThis.data=eval(data);this.zoneid=aThis.data['ZONEID'];aThis.DrawBanner();}}
var d=document.domain;d=d.replace('www.','');var url=d.split('.');output='<a href="http://admarket.admicro.vn/?utm_source=box_'+url[0]+'&utm_medium=banner&utm_campaign=PR06201" target="_blank"><div class="specLeftContent"></div></a>';output+='<div class ="adv_item"><div id="ssvzone_1626_items"></div></div>';try{var docZone=document.getElementById('ssvzone_1626');try{if(docZone){docZone.innerHTML=output;}}catch(e){}}catch(e){}
try{var self32_850_110=new AdmZones();self32_850_110.InitScript();self32_850_110.OnLoaded({"ZONEID":1626,"DATA":{"1":{"bid":"88964","cid":"1004352","title":"Th\u1eafp s\u00e1ng da t\u1ea1i TMV Nh\u1eadt Vy - Gi\u1ea3m <b style=\"font-weight:bold;color:#fc0203;\">69%<\/b>","link":"http:\/\/muachung.vn\/san-pham\/p-2151\/Tham-My-Vien-Nhat-Vy.html","content":"Ch\u1ec9 <b style=\"font-weight:bold;color:#fc0203;\">155.000\u0111<\/b> c\u00f3 thanh t\u1ea9y da to\u00e0n th\u00e2n ho\u1eb7c \u0111\u1eafp m\u1eb7t Collagen t\u1ea1i TMV Nh\u1eadt Vy gi\u00e1<b> 500.000\u0111<\/b>","descr":"muachung.vn","image":"http:\/\/admicro2.vcmedia.vn\/adt\/cpc\/ssvimg\/2011\/12\/05255-21324373056.jpg","l":"1,2,3","la":""},"2":{"bid":"87960","cid":"1004352","title":"R\u01b0\u1ee3u vang Ph\u00e1p St\u00e9phanie 888 - Gi\u1ea3m <b style=\"font-weight:bold;color:#fc0203;\">40%<\/b>","link":"http:\/\/muachung.vn\/san-pham\/p-2029\/Ruou-vang-Stephanie-888-Giam-40.html","content":"Ch\u1ec9 <b style=\"font-weight:bold;color:#fc0203;\">363.000\u0111 <\/b>c\u00f3 r\u01b0\u1ee3u vang St\u00e9phanie h\u1ea3o h\u1ea1ng th\u00edch h\u1ee3p l\u00e0m qu\u00e0 t\u1eb7ng d\u1ecbp T\u1ebft gi\u00e1 <b>605.000\u0111<\/b>","descr":"muachung.vn","image":"http:\/\/admicro2.vcmedia.vn\/adt\/cpc\/ssvimg\/2011\/12\/03364-41323942053.jpg","l":"4,17,10,14,29,21,26,22","la":"1"},"3":{"bid":"88984","cid":"1004352","title":"G\u1ed1i ng\u1ee7 \u0111a n\u0103ng ti\u1ec7n l\u1ee3i -  Gi\u1ea3m <b style=\"font-weight:bold;color:#fc0203;\">52%<\/b>","link":"http:\/\/muachung.vn\/san-pham\/p-1983\/Goi-ngu-da-nang-Giam-52.html","content":"Ch\u1ec9 <b style=\"font-weight:bold;color:#fc0203;\">170.000\u0111 <\/b>c\u00f3 g\u1ed1i ng\u1ee7 \u0111a n\u0103ng b\u1eb1ng cotton, \u0111\u1ec7m b\u00f4ng m\u1ec1m cho gi\u1ea5c ng\u1ee7 s\u00e2u gi\u00e1 <b>350.000\u0111<\/b>","descr":"muachung.vn","image":"http:\/\/admicro2.vcmedia.vn\/adt\/cpc\/ssvimg\/2011\/12\/07699-21324529678.jpg","l":"4,17,10,14,29,21,26,22","la":"1"},"4":{"bid":"89643","cid":"1013592","title":"B\u1ea1n mu\u1ed1n gi\u1ecfi ti\u1ebfng Anh?","link":"http:\/\/www.TiengAnh123.com\/qc\/hta8.html","content":"H\u1ecdc ti\u1ebfng Anh tr\u1ef1c tuy\u1ebfn 24\/24, 7\/7, hi\u1ec7u qu\u1ea3, ch\u1ea5t l\u01b0\u1ee3ng ch\u1ec9 v\u1edbi <b style=\"font-weight:bold;color:#fc0203;\">200,000 vn\u0111<\/b> \/1 n\u0103m.","descr":"TiengAnh123.com","image":"http:\/\/admicro2.vcmedia.vn\/adt\/cpc\/ssvimg\/2011\/12\/learn-21324551139.jpg","l":"1,2,3","la":""}}});}
catch(e){if(self32_850_110.campaignAvaiLen==0)document.getElementById('ssvzone_1626').style.display='none';}