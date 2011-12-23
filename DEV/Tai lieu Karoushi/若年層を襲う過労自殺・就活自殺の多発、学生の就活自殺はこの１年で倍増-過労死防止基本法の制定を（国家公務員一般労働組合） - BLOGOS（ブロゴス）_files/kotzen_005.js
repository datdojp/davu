kotzen.abusiveness = { 
    report_at_forum_response : function(forum_response_id){
              $.ajax({
                 type: "POST",
                 cache : false,
                 url: "/api/my/abusiveness_report/report_at_forum_response/",
                 data : {  forum_response_id : forum_response_id , _token : $.cookie('_token') },
                dataType:"json",
                 success: function(json){
                    $.colorbox({width:"650px", inline:true, href:"#forum_response_abusiveness_report_panel" , opacity:0.7 });
                 }
                });
    }
};
