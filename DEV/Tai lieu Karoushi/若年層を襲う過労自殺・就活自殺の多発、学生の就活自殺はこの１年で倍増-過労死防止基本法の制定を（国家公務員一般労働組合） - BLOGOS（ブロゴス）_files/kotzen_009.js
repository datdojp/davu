kotzen.article = { 
    navigator_item_count : 0,
    navigator_width : 0,
    navigator_item_width:190,
    navigator_prev : null,
    navigator_next : null,
    navigator_axis : undefined,
// TODO : threadを考慮してlockする
    navigate_to_article : function(article_id,items){
        var width = document.documentElement.clientWidth;
        var row_num = 0;
        for(var i = 0 ; i < items.length;i++){
            if( article_id == items[i].id ) {
                row_num = i+1;

                // 次の記事
                if(items.length > i){
                    $('#article_pre_next_container').show();
                    this.navigator_next = items[i+1];
                }
                
                if(i>0){
                    $('#article_pre_next_container').show();
                    this.navigator_prev = items[i-1];
                }

            }
        }
        var scroll_size = row_num * this.navigator_item_width - (width/2);
        if(scroll_size <= 0 ){
            scroll_size = 0 - 20;
        }
        $('#ul_axis_articles').css('left',scroll_size*-1);
    },
// TODO: lock
    forward_navigator : function(){
        var width = document.documentElement.clientWidth;
        var scroll_size = width ;
        var cur = $('#ul_axis_articles').css('left').replace(/px$/,'');
        
        // 進みすぎないように
        if(cur*-1 + scroll_size  > this.navigator_width ){
            $('#forward_navigator').attr('class','off');
            return;
        }


        $('#ul_axis_articles').animate({'left': '-=' + scroll_size + 'px'}, 'slow');
        $('#rewind_navigator').attr('class','on');



    },
// TODO: lockの仕方がようわからん
    rewind_navigator : function(){
        var width = document.documentElement.clientWidth;
        var scroll_size = width - 100;
        var cur = $('#ul_axis_articles').css('left').replace(/px$/,'');

        // 一番もどってる
        if(cur >= 20 ) {
            $('#rewind_navigator').attr('class','off');
            return;
        }

        // 戻りすぎないように
        if( cur*-1 < scroll_size ) {
            scroll_size = cur*-1 + 20;
            $('#rewind_navigator').attr('class','off');
        }
        $('#ul_axis_articles').animate({'left': '+='+ scroll_size + 'px'}, 'slow');;   
        $('#forward_navigator').attr('class','on');
        return;
    },
    toggle_navigator : function(){
        var container =  $('#navigator-articles-container');
        if(container.is(':visible')){
            kotzen.article.hide_navigator();
        }
        else {
            kotzen.article.show_navigator();
        }

    },
    hide_navigator : function(){
            var out_container=  $('#axis_navigator_container');
            var container =  $('#navigator-articles-container');
            var button =  $('#navigator_display_button');
            container.hide();
            out_container.css('height','29px');
            button.attr('class','open');
            $.cookie('hide_article_navi',true,{ expires: 30 , path : '/' })
    },
    show_navigator : function(){
            var out_container=  $('#axis_navigator_container');
            var container =  $('#navigator-articles-container');
            var button =  $('#navigator_display_button');
            container.show();
            out_container.css('height','100px');
            button.attr('class','close');
            $.cookie('hide_article_navi', '',{ expires: -1 , path : '/' });
    },
    navigator : function(article_id){
             if( !$.cookie('hide_article_navi')  ){
                kotzen.article.show_navigator();
             }
            var params = kotzen.utils.queryParams();
              $.ajax({
                 type: "POST",
                 cache : false,
                 url: "/api/article/navigator/",
                 data : { article_id : article_id , axis : params.axis },
                dataType:"json",
                 success: function(json){
                    if(!json.error){
                        json.article_id = article_id;
                        var html_axis_suggest = kotzen.utils.tmpl('tmpl_axis_suggest',json);       
                        $('#axis_suggest_container').html( html_axis_suggest );
                        kotzen.article.navigator_articles(json.axis_info.axis,article_id);
                    }
                 }
                });
    },
    navigator_articles : function(axis,article_id){
              $('#axis_articles').html('LOADING...');

              $('.axis_suggest_tab').each(function(){
                    if(axis == $(this).attr('rel') ) {
                        $(this).attr('class','axis_suggest_tab on');
                    }
                    else {
                        $(this).attr('class','axis_suggest_tab');
                    }

              });

                kotzen.article.navigator_axis = axis;
              $.ajax({
                 type: "POST",
                 cache : false,
                 url: "/api/article/navigator/articles/",
                 data : { article_id : article_id , axis : axis },
                dataType:"json",
                 success: function(json){
                    if(!json.error){
                        var items =  json.items;
                        kotzen.article.navigator_item_count  = items.length;
                        kotzen.article.navigator_width  = items.length * kotzen.article.navigator_item_width;
                        json.axis = axis;
                        json.width = kotzen.article.navigator_width;
                        json.article_id = article_id;
                        var html = kotzen.utils.tmpl('tmpl_axis_articles',json);       
                        $('#axis_articles').html( html );

                        kotzen.article.navigator_prev = null;
                        kotzen.article.navigator_next = null;
                        if(items.length > 0){
                            kotzen.article.navigator_next = items[0];
                        }

                        if(article_id){
                            // こん中で prev / next の設定しなおしたりしてる
                            kotzen.article.navigate_to_article( article_id ,items);
                        }

                        if( kotzen.article.navigator_prev == null ) {
                            $('#navigator-prev-page-link').attr('class','off');
                        }
                        else {
                            var item = kotzen.article.navigator_prev;
                            item.axis = axis;
                            item.pager_type = 'prev';
                            $('#prev-article').html( kotzen.utils.tmpl('tmpl_article_box',item ) );
                            $('#navigator-prev-page-link').attr('class','on');
                        }

                        if( kotzen.article.navigator_next == null ) {
                            $('#navigator-next-page-link').attr('class','off');
                        }
                        else {
                            var item = kotzen.article.navigator_next;
                            item.axis = axis;
                            item.pager_type = 'next';
                            $('#next-article').html( kotzen.utils.tmpl('tmpl_article_box',item ) );
                            $('#navigator-next-page-link').attr('class','on');
                        }
                    }
                 }
                });
    },
    toPrevPage : function(){
        if( this.navigator_prev  != null ){
            var path = '/article/' + this.navigator_prev.id + '/';
            if(this.navigator_axis){
                path += '?axis=' + kotzen.article.navigator_axis;
            }
            location.href = path;
        }
    },
    toNextPage : function(){
        if( this.navigator_next  != null ){
            var path = '/article/' + this.navigator_next.id + '/';
            if(this.navigator_axis){
                path += '?axis=' + kotzen.article.navigator_axis;
            }
            location.href = path;
        }

    },
    toArticle : function(axis,id){
        var path = '/article/' + id + '/?axis=' + axis;
        location.href = path;
    }

};
