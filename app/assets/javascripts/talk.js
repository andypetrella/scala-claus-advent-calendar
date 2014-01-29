//GLOBALS VARS
function Talk(day) {
  this.currentDay = day;

  var main = $("#pt-main");

  var dayPage = function (day) {
    return main.find(".pt-page-"+day);
  };

  this.start = function() {
    window.globalClock.start();
    window.dayClock.start();
    this.wakeUp();
    this.retries = 0;
  };

  this.yesterdayPage = function () {
    return dayPage(this.currentDay-1);
  };

  this.todayPage = function () {
    return dayPage(this.currentDay);
  };

  this.fetchDay = function (day) {
    var me = this;
    var nextPage = dayPage(day);
    var ajax = jsRoutes.controllers.Application.day(day, window.godMode).ajax();
    ajax.done(function(html) {
      me.retries = 0;
      nextPage.remove();
      var current = dayPage(day-1);

      nextPage = $("<div class='pt-page pt-page-"+day+"'></div>");
      main.append(nextPage);

      nextPage.html(html);

      current.addClass("pt-page-rotateFall pt-page-ontop");
      nextPage.addClass("pt-page-current pt-page-scaleUp done");
    });
    ajax.fail(function(resp) {
      if (resp.status == 501) {
        nextPage.remove();
        if (!me.retries) {
          // restart the clock
          window.dayClock.stop();
          window.dayClock.setTime(3*60);
          window.dayClock.start();
        }
        me.retries++;
        var current = dayPage(day-1);

        nextPage = $("<div class='pt-page todo pt-page-"+day+"'></div>");
        main.append(nextPage);

        nextPage.html(resp.responseText);

        current.addClass("pt-page-rotateFall pt-page-ontop");
        nextPage.addClass("pt-page-current pt-page-scaleUp");
      } else {
        alert("That's unexpected...");
        a = window.open("http://localhost:9000", '_blank');
        a.document.write(resp.responseText);
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
  this.saveCode = function(day, code, file, retried) {
    var me = this;
    jsRoutes.controllers.Application.save(day, file).ajax({
      data:code,
      processData: false,
      contentType:"text/plain; charset=utf-8"
    })
    .done(function(data) {
      //reload the page
      me.wakeUp()
        .fail(function(resp) {
          if (resp.status != 501) {
            if (!retried) {
              return me.saveCode(day, code, file, true);
            }
          } else {
            return resp;
          }
        });
    });
  };

}


//BEHAVIOR
$(document).on("keyup", function(event) {
  var focusInEditor = $(document.activeElement).parents("div.editor").length;
  if (!focusInEditor) {
    var currentPage = $(".pt-page-"+talk.currentDay+" .row");

    if (event.keyCode==39 && talk.todayPage().hasClass("done")) { //right
      talk.goToSleep();
    } else if (event.keyCode == 37) { //left
      talk.rewind();
    } else if (event.keyCode == 70) { // f
      if (event.ctrlKey && event.altKey && window.currrentEditor) {
        window.currrentEditor.toggleFullScreen();
      } else {
        currentPage.trigger('close');
      }
    } else if (event.keyCode == 67) { // c
      currentPage.trigger('open-code');
    } else if (event.keyCode == 82) { // r
      currentPage.trigger('open-result');
    } else if (event.keyCode == 84) { // t
      currentPage.trigger('open-text');
    } else if (event.keyCode == 79) { // o
      if (event.shiftKey) {
        currentPage.trigger('open-wide');
      } else {
        currentPage.trigger('open');
      }
    } else if (event.keyCode == 83) { // s
      if (event.ctrlKey && event.altKey && window.globalClock) {
        window.globalClock.start();
      }
    }
  }
});