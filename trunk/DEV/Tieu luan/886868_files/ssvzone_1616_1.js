
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
this.CreateStyleSheet=function(){var css='#ssvzone_1616{clear:both;width:180px;height:490px;text-align:left;overflow:hidden;margin:0 auto}#ssvzone_1616 *{text-decoration:none;font-family:tahoma,arial!important;_font-family:tahoma, arial;font-size:11px;color:#333;text-transform:none!important;_text-transform:none;margin:0;padding:0}#ssvzone_1616 .ssvzTop,#ssvzone_1616 .ssvzBottom{clear:both;height:3px;overflow:hidden}#ssvzone_1616 .ssvzTop{background:url('+imgHost+'ssvborder.png) no-repeat 0 0}#ssvzone_1616 .ssvzTop .ssvzRight{background:url('+imgHost+'ssvborder.png) no-repeat 100% -3px}#ssvzone_1616 .ssvzTop .ssvzMid{background:url('+imgHost+'ssvborder.png) repeat-x 0 -12px;height:3px;margin:0 3px}#ssvzone_1616 .ssvzBottom{background:url('+imgHost+'ssvborder.png) no-repeat 0 -9px}#ssvzone_1616 .ssvzBottom .ssvzRight{background:url('+imgHost+'ssvborder.png) no-repeat 100% -6px}#ssvzone_1616 .ssvzBottom .ssvzMid{background:url('+imgHost+'ssvborder.png) repeat-x 0 -15px;height:3px;margin:0 3px}#ssvzone_1616 .ssvzContent{clear:both;overflow:hidden;background:url('+imgHost+'ssvtop.png) repeat-y 0 0}#ssvzone_1616 .ssvzContent .ssvzRight{background:url('+imgHost+'ssvtop.png) repeat-y 100% 0}#ssvzone_1616 .ssvzContent .ssvzMid{background:#FFF;height:440px;margin:0 3px;}#ssvzone_1616 .ssvzHeader{background:url('+imgHost+'ssvborder.png) repeat-x 5px -18px;height:21px;line-height:21px}#ssvzone_1616 .ssvHeaderBr{background:url('+imgHost+'ssvtop.png) repeat-y 0 0; clear:both; overflow: hidden; padding-left: 3px;}#ssvzone_1616 .ssvHeaderBrRight{background:url('+imgHost+'ssvtop.png) repeat-y 100% 0;padding-right: 3px;}#ssvzone_1616 .ssvzLogo{border:0;width:140px;height:21px;line-height:21px;background:url('+imgHost+'ssvtop.png) no-repeat -3px 0;margin:0;padding:0}#ssvzone_1616 .ssvzBuy{padding:0 !important;border:0;width:140px;height:21px;background:url('+imgHost+'ssvtop.png) no-repeat -3px -21px}#ssvzone_1616 .ssvzclear{clear:both}#ssvzone_1616 #ssvzone_1616_items{clear:both;margin:0;}#ssvzone_1616_items .adv_items{clear:both;height:215px;overflow:hidden;padding-top:5px;padding-right:7px;margin-left:7px;}#ssvzone_1616_items .adv_items .ssvzimage{clear:both;}#ssvzone_1616_items .adv_items .ssvzimage img{float:left;border:0;width:90px;height:90px;margin-right:5px}#ssvzone_1616_items .adv_items .ssvzTitle{clear:both;text-align:left;overflow:hidden;height:auto;}#ssvzone_1616_items .adv_items .ssvzTitle a:link,#ssvzone_1616_items .adv_items .ssvzTitle a:visited{color:#333;font-size:11px;font-weight:700!important;_font-weight:700;font-family:tahoma, arial,verdana;text-decoration:none;text-align:center;padding-top:0}#ssvzone_1616_items .itemmc{clear:both;float:left;margin-bottom:3px;height:14px;overflow:hidden;line-height:12px;text-align:left}#ssvzone_1616_items .itemmc,#ssvzone_1616_items .itemmc a:visited,#ssvzone_1616_items .itemmc a:active,#ssvzone_1616_items .itemmc a:link{font-weigth:normal;text-decoration:none;color:#666}#ssvzone_1616_items .adv_items .contentAds{padding-top:3px;text-align:left;line-height:16px;overflow:hidden;float:left;width:134px;height:80px}#ssvzone_1616_items .adv_items .contentAds a:link,#ssvzone_1616_items .adv_items .contentAds a:visited{line-height:15px;text-decoration:none;font-family:Tahoma;font-size:11px;font-weight:400;color:#333}#ssvzone_1616_items .ssvzBorder{background:#e5e5e5;height:1px}',doc=document,style=doc.createElement('style');style.type='text/css';var head=doc.getElementsByTagName('head')[0];head.appendChild(style);if(style.styleSheet)style.styleSheet.cssText=css;else style.appendChild(doc.createTextNode(css));}
this.InitScript=function(){this.CreateStyleSheet();}
this.escapeHtml=function(str){var stra=str.replace(/<(span|strong|b)[^>]*>(.*?)<\/(span|strong|b)>/g,'$2');stra=stra.replace(/\"/g,'&quot;');return stra;}
this.PArray=function(a,b,c){var x=-1;for(i=0;i<c;i++){if(b[i]['bid']==a){x=i;}}
return x;}
this.trackBanner=function(i,a,b){var d=a;var cAllI=b.length,bPost=-1;bPost=this.PArray(i,b,cAllI);if(bPost<0){d=a;}else{var cA=a.length,biPos=-1;biPos=this.PArray(i,a,cA);if(biPos>=0){d=a;}else{a[0]=b[bPost];d=a;}}
return d;}
this.DrawBanner=function(){var json=aThis.data,output='',allItems=[];this.zoneid=json['ZONEID'];var docBizone=document.getElementById('ssvzone_1616_items');this.camplist=this.campWillRun(this.toArray(json['DATA']));var cookCpRunned=this.cookCpRunned(),campLen=this.camplist.length,banLog='';trackI='';if(window["ADS_CHECKER"]!=undefined&&window["ADS_CHECKER"]!="undefined"){trackI=ADS_CHECKER.getQuery('i');}
if(campLen<=this.rand_limit){var Items=this.Shuffle(this.camplist),iLen=Items.length,lastI=[],k=0;for(var j=0;j<iLen;j++){if(lastI.length==0){lastI.push(Items[j]);k=k+1;}else{if(lastI.indexOf(Items[j])==-1){lastI.push(Items[j]);k=k+1;}}
if(k==this.rand_limit)break;}
Items=lastI;Items=this.trackBanner(trackI,Items,this.toArray(json['DATA']));Items=this.Shuffle(Items);var addI=this.rand_limit-Items.length,j=0;this.camplist=this.Shuffle(this.camplist);var camplistadd=this.camplist;if(typeof(_ssv_default)!=undefined&&typeof(_ssv_default)!='undefined'){_ssv_default=this.Shuffle(_ssv_default);if(_ssv_default.length>0){camplistadd=_ssv_default;}}
campLen=camplistadd.length;for(var i=0;i<addI;i++){if(j<campLen){Items.push(camplistadd[j]);}else{camplistadd=this.Shuffle(camplistadd);j=0;Items.push(camplistadd[j]);}
j++;}
if(campLen==0){var ssvzone_1616=document.getElementById('ssvzone_1616');ssvzone_1616.style.display='none';return false;}}else if(campLen>this.rand_limit){var Items=this.Shuffle(this.camplist),iLen=Items.length,lastI=[],k=0;for(var j=0;j<iLen;j++){if(lastI.length==0){lastI.push(Items[j]);k=k+1;}else{if(lastI.indexOf(Items[j])==-1){lastI.push(Items[j]);k=k+1;}}
if(k==this.rand_limit)break;}
Items=lastI;Items=this.trackBanner(trackI,Items,this.toArray(json['DATA']));Items=this.Shuffle(Items);if(Items.length<this.rand_limit){var slotI=this.rand_limit-Items.length;for(var i=0;i<=slotI;i++){Items.push(Items[i]);}}}
var ri=this.randNum(),rdca=(this.rand*9),curDom=document.domain;for(var j=0;j<this.rand_limit;j++){if(iLen>this.rand_limit){this.setCookie('cpcSelfServ',this.zoneid+'_'+Items[j]['bid']+',',1,'');}
if(this.bannerRun.indexOf(Items[j]['bid'])==-1){this.bannerRun.push(Items[j]['bid']);banLog=banLog+Items[j]['cid']+'|'+Items[j]['bid']+',';}
var itemCss=(j%2==0)?'item_left':'item_right';var logurl='http://logging.admicro.vn/_adc.html?adm_domain='+escape(this.channel)+'&adm_campaign='+Items[j]['cid']+'&adm_aditem='+Items[j]['bid']+'&adm_zoneid='+this.zoneid+'&adm_channelid=-1&adm_rehttp='+escape(Items[j]['link'])+'&adm_random='+(this.rand*9)+'';var logurl1='http://logging.admicro.vn/_adc.html?adm_domain='+escape(this.channel)+'&adm_campaign='+Items[j]['cid']+'&adm_aditem='+Items[j]['bid']+'&adm_zoneid='+this.zoneid+'&adm_channelid=-1&adm_rehttp='+escape(Items[j]['link']+'?ref='+curDom+'&bi='+Items[j]['bid']+'&ci='+Items[j]['cid'])+'&adm_random='+rdca+'';lineBorder='';var anC='';if(Items[j]['bid']){anC='<div id="'+this.zoneid+'_'+Items[j]['bid']+'" style="height:0px;width:0px;overflow:hidden;"><span></span></div>';}
if(Items[j]['descr'].indexOf('muachung.vn')!=-1){logurl=logurl1;}
var title=this.escapeHtml(Items[j]['title']);if(window["ADS_CHECKER"]!=undefined&&window["ADS_CHECKER"]!="undefined"){var qr=ADS_CHECKER.getQuery('admdebug');if(qr!=''){title='B='+Items[j]['bid']+', L='+Items[j]['l']+', LA='+Items[j]['la']+', R='+ADS_CHECKER.getCookie('__R')+', RC='+ADS_CHECKER.getCookie('__RC');}}
output+='<div class="adv_items" id="adv_item">'+anC+'<div class="ssvzTitle"><a href="'+logurl+'" target="_blank" title="'+title+'">'+Items[j]['title']+'</a></div><div class ="itemmc"><a href="'+logurl+'" target="_blank" title="'+this.escapeHtml(Items[j]['descr'])+'">'+Items[j]['descr']+'</a></div><div class="ssvzimage" ><a href="'+logurl+'" target="_blank" title="'+title+'"><img align="left" src="'+imgHost+'zoneimages/spacer.gif" vspace="0" hspace="0" border="0" alt="'+title+'" style=" background:url(\''+Items[j]['image']+'\') no-repeat center center" /></a></div><div class="contentAds"><a href="'+logurl+'" target="_blank" title="'+this.escapeHtml(Items[j]['content'])+'">'+Items[j]['content']+'</a></div></div>'+lineBorder;if(j<(this.rand_limit-1)){output+='<div class="ssvzBorder"><span></div>';}}
if(docBizone){docBizone.innerHTML=output;}
if(document.images&&banLog!=''){var img=new Image();img.src='http://logging.admicro.vn/_zc.1.gif?_domain='+escape(this.channel)+'&_ci='+banLog.slice(0,-1)+'&_zid='+this.zoneid;}}
this.OnLoaded=function(data,methodName){aThis.data=eval(data);aThis.DrawBanner();}}
var d=document.domain;d=d.replace('www.','');var url=d.split('.');output='<div class="ssvzTop"><div class="ssvzRight"><div class="ssvzMid"><span></span></div></div></div>';output+='<div class="ssvHeaderBr"><div class="ssvHeaderBrRight"><div class="ssvzHeader">';output+='<a title="Mua quảng cáo"  href="http://admarket.admicro.vn/?utm_source=box_'+url[0]+'&utm_medium=banner&utm_campaign=PR06201" target="_blank"><img vspace="0" hspace="0" class="ssvzBuy" src="http://admicro2.vcmedia.vn/adt/cpc/zoneimages/spacer.gif" border="0" align="right" /></a></div></div></div>';output+='<div class="ssvzContent"><div class="ssvzRight"><div class="ssvzMid">';output+='<div id="ssvzone_1616_items"></div><div class="ssvzclear"><span></span></div>';output+='</div></div></div>';output+='<div class="ssvHeaderBrRight"><div class="ssvzHeader"><div class="ssvHeaderBrRight"> ';output+='<a title="Mua quảng cáo" href="http://admarket.admicro.vn/?utm_source=box_'+url[0]+'&utm_medium=banner&utm_campaign=PR06201" target="_blank"><img class="ssvzLogo" vspace="0" hspace="0" border="0" align="left" src="http://admicro2.vcmedia.vn/adt/cpc/zoneimages/spacer.gif"></a></div></div></div>';output+='<div class="ssvzBottom"><div class="ssvzRight"><div class="ssvzMid"><span></span></div></div></div>';try{var docZone=document.getElementById('ssvzone_1616');try{if(docZone){docZone.innerHTML=output;}}catch(e){}}catch(e){}try{var self27_180_490=new AdmZones();self27_180_490.InitScript();self27_180_490.OnLoaded({"ZONEID":1616,"DATA":{"1":{"bid":"74987","cid":"1008202","title":"Sofa cao c\u1ea5p HH decor","link":"http:\/\/hhdecor.vn\/","content":"Khuy\u1ebfn m\u1ea1i 4 g\u1ed1i tr\u1ecb gi\u00e1<b style=\"font-weight:normal;color:#fc0203;\"> 2.000.000\u0111<\/b> khi \u0111\u1eb7t sofa t\u1ea1i HHdecor-  Ms Hi\u1ec1n 0947866656","descr":"hhdecor.vn","image":"http:\/\/admicro2.vcmedia.vn\/adt\/cpc\/ssvimg\/2011\/09\/2010--21324840022.jpg","l":"1","la":""},"2":{"bid":"87588","cid":"1009787","title":"\u0110\u00f3n b\u00ecnh minh tr\u00ean V\u1ecbnh H\u1ea1 Long-Gi\u1ea3m <b style=\"font-weight:bold;color:#fc0203;\">36%<\/b>","link":"http:\/\/muachung.vn\/san-pham\/p-1819\/Tour-Tham-quan-Ha-Long-bang-du-thuyen-Dolphin.html","content":"Ch\u1ec9 <b style=\"font-weight:bold;color:#fc0203;\">1.399.000\u0111<\/b>\/ ng\u01b0\u1eddi c\u00f3 2ng\u00e0y 1\u0111\u00eam \u0111i V\u1ecbnh H\u1ea1 Long v\u1edbi du thuy\u1ec1n Dolphin gi\u00e1 <b>2.195.000\u0111<\/b>","descr":"muachung.vn","image":"http:\/\/admicro2.vcmedia.vn\/adt\/cpc\/ssvimg\/2011\/12\/12-14-41323830756.jpg","l":"2,1","la":""},"3":{"bid":"89554","cid":"1013559","title":"Trang s\u1ee9c H\u00e0n Qu\u1ed1c s\u00e0nh \u0111i\u1ec7u v\u00e0 l\u1ea5p l\u00e1nh","link":"http:\/\/enbac.com\/Ha-Noi\/c45\/Trang-suc?utm_source=tonghop&utm_medium=CPC&utm_content=trangsuc&utm_campaign=trangsucB","content":"B\u1ea1n s\u1ebd th\u1eadt c\u00e1 t\u00ednh v\u00e0 s\u00e0nh \u0111i\u1ec7u v\u1edbi trang s\u1ee9c H\u00e0n Qu\u1ed1c c\u1ef1c xinh x\u1eafn. Gi\u1ea3m gi\u00e1 t\u1eeb <b style=\"font-weight:bold;color:#fc0203;\">30%<\/b>","descr":"enbac.com","image":"http:\/\/admicro2.vcmedia.vn\/adt\/cpc\/ssvimg\/2011\/12\/t6704-21324539297.jpg","l":"1","la":""},"4":{"bid":"89936","cid":"1013666","title":"Ti\u1ebfng Anh ph\u1ecfng v\u1ea5n xin vi\u1ec7c - gi\u1ea3m <b style=\"font-weight:bold;color:#fc0203;\">30%<\/b>","link":"http:\/\/cfm.edu.vn\/news\/detail\/171\/tieng-anh-phong-van-xin-viec","content":"T\u1ef1 tin v\u1edbi kh\u00f3a h\u1ecdc Ti\u1ebfng Anh ph\u1ecfng v\u1ea5n xin vi\u1ec7c. <b style=\"font-weight:normal;color:#fc0203;\">H\u1ecdc th\u1eed<\/b> mi\u1ec5n ph\u00ed. Li\u00ean h\u1ec7: <b>0164 6660350<\/b>","descr":"cfm.edu.vn","image":"http:\/\/admicro2.vcmedia.vn\/adt\/cpc\/ssvimg\/2011\/12\/08181-21324643084.jpg","l":"4,17,10,14,29,21,26,22","la":"1"}}});}
catch(e){if(self27_180_490.campaignAvaiLen==0)document.getElementById('ssvzone_1616').style.display='none';}