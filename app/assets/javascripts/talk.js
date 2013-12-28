//GLOBALS VARS
function Talk(day) {
    this.currentDay = day;

    var main = $("#pt-main");

    var dayPage = function (day) {
        return main.find(".pt-page-"+day);
    };

    this.yesterdayPage = function () {
        return dayPage(this.currentDay-1);
    };

    this.todayPage = function () {
        return dayPage(this.currentDay);
    };

    this.fetchDay = function (day) {
        var nextPage = dayPage(day);
        nextPage.remove();
        var ajax = jsRoutes.controllers.Application.day(day).ajax();
        ajax.done(function(html) {
            var current = dayPage(day-1);

            nextPage = $("<div class='pt-page pt-page-"+day+"'></div>");
            main.append(nextPage);

            nextPage.html(html);

            current.addClass("pt-page-rotateFall pt-page-ontop");
            nextPage.addClass("pt-page-current pt-page-scaleUp done");
        });
        ajax.fail(function(resp) {
            if (resp.status == 501) {
                var current = dayPage(day-1);

                //if (!nextPage.length) {
                nextPage = $("<div class='pt-page todo pt-page-"+day+"'></div>");
                main.append(nextPage);
                //}
                nextPage.html(resp.responseText);

                current.addClass("pt-page-rotateFall pt-page-ontop");
                nextPage.addClass("pt-page-current pt-page-scaleUp");
            } else {
                alert("That's unexpected...");
                console.error(resp);
            }
        });
        return ajax;
    };
    this.wakeUp = function () {
        return this.fetchDay(this.currentDay);
    };
    this.goToSleep = function () {
        this.currentDay ++;
        var today = this.todayPage();
        if (today.length && today.hasClass("done")) {
            //show page
            yesterdayPage()
                .addClass("pt-page-rotateFall pt-page-ontop");
            today.addClass("pt-page-current pt-page-scaleUp done");
        } else {
            //fetch page
            this.wakeUp();
        }
    };
    this.rewind = function () {
        if (this.currentDay == 1) {
            return;
        }
        //show page
        var today = this.todayPage();
        today.remove();

        var yesterday = this.yesterdayPage();
        this.currentDay --;
        var yesterdayDone = yesterday.hasClass("done");
        yesterday.attr("class", 'pt-page pt-page-'+this.currentDay);
        if (yesterdayDone) {
            yesterday.addClass("done");
        }
        yesterday.removeClass("pt-page-rotateFall pt-page-ontop");
        yesterday.addClass("pt-page-current pt-page-scaleUp");
    };

}


//BEHAVIOR
$(document).on("keyup", function(event) {
    var focusInEditor = $(document.activeElement).parents("div.editor").length;
    if (!focusInEditor) {
        if (event.keyCode==39 && talk.todayPage().hasClass("done")) { //right
            talk.goToSleep();
        } else if (event.keyCode == 37) { //left
            talk.rewind();
        } else if (event.keyCode == 187) { // +
            $(".pt-page-"+talk.currentDay+" .row").trigger('increase');
        } else if (event.keyCode == 189) { // -
            $(".pt-page-"+talk.currentDay+" .row").trigger('decrease');
        } else if (event.keyCode == 79) { // o
            $(".pt-page-"+talk.currentDay+" .row").trigger('open');
        } else if (event.keyCode == 70) { // f
            $(".pt-page-"+talk.currentDay+" .row").trigger('close');
        }

    }
});