kotzen.pageHistory = { 
    append  : function( type,id) {
         var ids = new kotzen.pageHistory.get(type);
         ids.unshift( id );
         var ids_str = ids.join(',').replace(/,$/,'');

         $.cookie('kotzen_ph_'  + type ,ids_str , { expires: 30 , path : '/' })
    },
    get : function( type ) {
        var ids_str = $.cookie('kotzen_ph_' + type ) || '';
        if(ids_str){
            return kotzen.pageHistory.uniq( ids_str.split(',') ).slice(0,10);
        }
        else {
            return new Array();
        }
    },
    load : function( args ) {
        var ids = kotzen.pageHistory.get( args.type );
        var str_ids = ids.join(',');
        var container_id = args.container_id ;

        if(str_ids){
              $.ajax({
                 type: "GET",
                 cache : false,
                 url: "/api/"+ args.type + "/list/",
                 data : { str_ids : str_ids },
                 dataType:"json",
                 success: function(json){
                    if(!json.error && json.items.length){
                        var html = kotzen.utils.tmpl('tmpl_sidepart_hisotry_' + args.type ,json);       
                        $(container_id).html( html );
                    }
                 }
                });
        }
    },
    uniq : function (arr) {
           var o = {},
           r = [];
           for (var i = 0;i < arr.length;i++) {
               if (arr[i] in o? false: o[arr[i]] = true) {
                   r.push(arr[i]);
                }
           }
           return r;
    }
};
