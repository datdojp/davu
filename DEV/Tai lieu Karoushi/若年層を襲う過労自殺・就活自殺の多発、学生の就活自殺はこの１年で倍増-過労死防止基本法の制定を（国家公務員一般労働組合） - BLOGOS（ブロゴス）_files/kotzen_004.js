kotzen.karma = { 
    down: function(t,type){
        var e = $(t);

        $.ajax({
            type: "POST",
            cache : false,
            url: "/api/karma/"+ type +"/"+ e.attr('rel') + "/down/",
            dataType:"json",
            data : { "_token" : $.cookie('_token') } ,
            success: function(json){
                if(!json.error){
                    if( json.code == 'DONE' ){
                        e.html( '<span class="button">もどす</span><span class="count"><em>' + json.num_karmas + '</em></span>' );
                        e.attr({
                            onClick : "kotzen.karma.up(this,'"+ type + "');",
                            'class' : 'karma-up-button'
                        });
                    }
                }
            }
        });
    },
    up : function(t,type){
        var e = $(t);
        $.ajax({
            type: "POST",
            cache : false,
            url: "/api/karma/"+ type +"/"+ e.attr('rel') + "/up/",
            dataType:"json",
            data : { "_token" : $.cookie('_token') } ,
            success: function(json){
                if(!json.error){
                    if(json.on_member){
                        if( json.code == 'DONE' ){
                            e.html( '<span class="button">支持する</span><span class="count"><em>' + json.num_karmas + '</em></span>' );
                            e.attr({
                                onClick : "kotzen.karma.down(this,'"+ type +"');",
                                'class' : 'karma-down-button'
                            });
                        }
                    }
                    else {
                        // 認証していない
                        // XXX PCしかうごかんかも。依存してる。
                        $('.login').click();
                    }
                }
            }
        });
    },
    // # 記事IDが重複してるケースも考慮してる
    get : function(type,class_name,on_readonly){
        var data = {};
        var ids = [];
        $('.' + class_name ).each(function(index){
            var e = $(this);
            if(!data[e.attr('rel')]){
                data[e.attr('rel')] = []; 
                ids.push(e.attr('rel'));
            }
            data[e.attr('rel')].push(e); 
        });

        var query = 'ids=' + ids.join(',');
        $.ajax({
            type: "GET",
            cache : false,
            url: "/api/karma/"+ type +"/?" + query,
            dataType:"json",
            success: function(json){
                if(!json.error){
                    var items = json.items;
                    for( var i =0 ; i < items.length;i++){
                        var ids = data[items[i].id];
                        for(var j=0; j < ids.length;j++){
                            if(on_readonly){
                                ids[j].html( '<span class="readonly">支持：' + items[i].num_karmas + '</span>');
                            }
                            else {
                                if( items[i].has_karma ){
                                    ids[j].html( '<a href="javascript:void(0);" rel="' + items[i].id+ '" onClick="kotzen.karma.down(this,\'' +type + '\');" class="karma-down-button"><span class="button">もどす</span><span class="count"><em>' + items[i].num_karmas + '</em></span></a>' );
                                }
                                else {
                                    ids[j].html( '<a href="javascript:void(0);" rel="' + items[i].id+ '" onClick="kotzen.karma.up(this,\'' + type + '\');" class="karma-up-button"><span class="button">支持する</span><span class="count"><em>' + items[i].num_karmas + '</em></span></a>' );
                                }
                            }
                        }
                    }
                }
            }
        });        
    }
};
