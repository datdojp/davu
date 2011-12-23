var kotzen = {};
kotzen.utils = { 
    subcut : function(str,num){
        if(str.length < num){
            return str;
        }
        else {
            return str.substr(0,num) + '...';
        }
    },
    unicodeEscape: function(str) {
        var code, pref = {1: '\\x0', 2: '\\x', 3: '\\u0', 4: '\\u'};
        return str.replace(/\W/g, function(c) {
            return pref[(code = c.charCodeAt(0).toString(16)).length] + code;
        });
    },
    tagSafe: function(str){
        return str.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\"/g,'&quot;');
    },
    tmpl: function (id,data) { // original by John Resig http://ejohn.org/ 'JavaScript Micro-Templating' - MIT Licensed.
        var tmpl_data = $('#' + id).html();
        var fn = new Function("obj",
                     "var p=[];" +

                     // Introduce the data as local variables using with(){}
                     "with(obj){p.push('" +

                     // Convert the template into pure JavaScript
                    tmpl_data 
                     .replace(/[\r\t\n]/g, " ")
                     .split("<%").join("\t")
                     .replace(/(^|%>)[^\t]*?(\t|$)/g, function(){return arguments[0].split("'").join("\\'");})
                     .replace(/\t==(.*?)%>/g,"',$1,'")
                     .replace(/\t=(.*?)%>/g, "',(($1)+'').replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\"/g,'&quot;'),'")
                     .split("\t").join("');")
                     .split("%>").join("p.push('")
                     + "');}return p.join('');");
        return fn( data );
    },
    queryParams : function() {
             var vars = [], hash;
             var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
             for(var i = 0; i < hashes.length; i++)
             {
                 hash = hashes[i].split('=');
                 vars.push(hash[0]);
                 vars[hash[0]] = hash[1];
             }
             return vars;
    }
};

// firebug console..
if (window.console && window.console.log ) {
    window.log = window.console.log
} else {
    window.console = {
log: function () {}
    }
}

