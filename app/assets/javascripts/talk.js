//GLOBALS VARS
function Talk(day) {
    var currentDay = day;

    var main = $("#pt-main");

    var dayPage = function (day) {
        return main.find(".pt-page-"+day);
    };

    var yesterdayPage = function () {
        return dayPage(currentDay-1);
    };

    this.todayPage = function () {
        return dayPage(currentDay);
    };

    this.fetchDay = function (day) {
        var nextPage = dayPage(day);
        nextPage.remove();
        var ajax = jsRoutes.controllers.Application.day(day).ajax();
        ajax.done(function(html) {
            var current = dayPage(day-1);

            //if (!nextPage.length) {
            nextPage = $("<div class='pt-page pt-page-"+day+"'></div>");
            main.append(nextPage);
            //}
            nextPage.html(html);

            //current.addClass("pt-page-rotateFall pt-page-ontop");
            //if (!nextPage.hasClass("pt-page-current")) {
                nextPage.addClass("pt-page-current pt-page-scaleUp done");
            //}
        });
        ajax.fail(function(resp) {
            if (resp.status == 501) {
                var current = dayPage(day-1);

                //if (!nextPage.length) {
                nextPage = $("<div class='pt-page pt-page-"+day+"'></div>");
                main.append(nextPage);
                //}
                nextPage.html(resp.responseText);

                current.addClass("pt-page-rotateFall pt-page-ontop");
                //if (!nextPage.hasClass("pt-page-current")) {
                    nextPage.addClass("pt-page-current pt-page-scaleUp");
                //}
            } else {
                alert("That's unexpected...");
                console.error(resp);
            }
        });
        return ajax;
    };
    this.wakeUp = function () {
        return this.fetchDay(currentDay);
    };
    this.goToSleep = function () {
        currentDay ++;
        var today = this.todayPage();
        if (today.length && today.hasClass("done")) {
            //show page
            yesterdayPage().addClass("pt-page-rotateFall pt-page-ontop");
            today.addClass("pt-page-current pt-page-scaleUp done");
        } else {
            //fetch page
            this.wakeUp();
        }
    };
    this.rewind = function () {
        //show page
        var today = this.todayPage();
        today.remove();

        var yesterday = yesterdayPage();
        currentDay --;
        var yesterdayDone = yesterday.hasClass("done");
        yesterday.attr("class", 'pt-page pt-page-'+currentDay);
        if (yesterdayDone) {
            yesterday.addClass("done");
        }

        yesterday.addClass("pt-page-current pt-page-scaleUp");
    };

}


//BEHAVIOR
//$("#next").on("click", goToSleep);
$(document).on("keyup", function(event) {
    var focusInEditor = $(document.activeElement).parents("div.editor").length;
    if (!focusInEditor) {
        if (event.keyCode==39 && talk.todayPage().hasClass("done")) //right
            talk.goToSleep();
        else if (event.keyCode == 37) //left
            talk.rewind();
    }
});