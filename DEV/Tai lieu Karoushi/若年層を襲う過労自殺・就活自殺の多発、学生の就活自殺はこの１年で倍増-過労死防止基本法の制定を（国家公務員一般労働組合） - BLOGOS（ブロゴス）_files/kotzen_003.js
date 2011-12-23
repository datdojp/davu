kotzen.analytics = { 
    type : {
        'article' : 0,
        'discussion' : 1, 
        'tag' : 2, 
        'channel' : 3, 
        'profile' : 4
    },
    increment_article : function(id,name){
        this.increment(this.type.article,id,name);
    },
    increment_discussion : function(id,name){
        this.increment(this.type.discussion,id,name);
    },
    increment_tag : function(id,name){
        this.increment(this.type.tag,id,name);
    },
    increment_channel : function(id,name){
        this.increment(this.type.channel ,id,name);
    },
    increment_profile : function(id,name){
        this.increment(this.type.profile,id,name);
    },
    increment : function(type,id,name){
              $.ajax({
                 type: "GET",
                 cache : false,
                 url: "/analytics/increment/",
                 data : { jorker_id : id ,analytics_type : type },
                dataType:"json",
                 success: function(json){
                    if(!json.error){
                        $('#' + name ).text(json.count);
                    }
                    else {
                        $('#' + name ).text('-');
                    }
                }
                });
    },
    get_article : function(id,name){
        this.get(this.type.article,id,name);
    },
    get : function(type,id,name){
              $.ajax({
                 type: "GET",
                 cache : false,
                 url: "/analytics/get/",
                 data : { jorker_id : id ,analytics_type : type },
                dataType:"json",
                 success: function(json){
                    if(!json.error){
                        $('#' + name ).text(json.count);
                    }
                    else {
                        $('#' + name ).text('-');
                    }
                }
                });
    }
};
