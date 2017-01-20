
(function ($) {

    var hash = window.location.hash, start = new Date();

    $(function () {
        //window.onbeforeunload = function () { return "您确定要退出本程序？"; };

        //window.mainpage.instMainMenus();
        //window.mainpage.instFavoMenus();
        //window.mainpage.instTimerSpan();
        window.mainpage.bindNavTabsButtonEvent();
        window.mainpage.bindToolbarButtonEvent();
        window.mainpage.bindMainTabsButtonEvent();


        var portal = $("#portal"), layout = $("#mainLayout"), navTabs = $("#navTabs"), themeCombo = $("#themeSelector");

        $.util.exec(function () {
            var theme = $.easyui.theme(), themeName = $.cookie("themeName");
            if (themeName && themeName != theme) { window.mainpage.setTheme(themeName, false); }

            layout.removeClass("hidden").layout("resize");

            $("#maskContainer").remove();

            var size = $.util.windowSize();
            //  判断当浏览器窗口宽度小于像素 1280 时，右侧 region-panel 自动收缩
            if (size.width < 1280) { layout.layout("collapse", "east"); }

            var stop = new Date();
            $.easyui.messager.show({ msg: "当前页面加载耗时(毫秒)：" + $.date.diff(start, "ms", stop), position: "bottomRight" });
        });

    });

    $.fn.datagrid.defaults.data = [];
    var onAfterRender = $.fn.datagrid.defaults.view.onAfterRender;
    $.extend($.fn.datagrid.defaults.view, {
        onAfterRender:function(target){
            onAfterRender.call(this,target);
            var dg = $(target);
            var data = dg.datagrid('getData');
            if (data.rows.length == 0){
                setTimeout(function(){
                    dg.datagrid('appendRow',{});
                    dg.datagrid('options').finder.getTr(target, 0).css('height',0).find('*').css('height',0);
                    data.total = 0;
                    data.rows = [];
                },0)
            }
        }
    });
})(jQuery);