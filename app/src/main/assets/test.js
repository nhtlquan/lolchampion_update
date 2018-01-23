function parseHTML1(Util, content){
    var doc = Util.getJsoup(content);
	var elementsa = doc.getElementsByTag("body").first()
	.getElementById("wrapper").getElementById("layout-main-container")
	.getElementById("layout-main").getElementById("layout-content")
	.getElementById("content").child(1)
	.getElementsByClass("bodyText ky-thu ky-thu-2");

	var elementsaImage = elementsa.first().getElementsByClass("text-center").first().getElementsByTag("img");
	var tempSrc = elementsaImage.attr("src");
	tempSrc = "http://tracuu.tuvisomenh.com" + tempSrc;
	elementsaImage.attr("src", tempSrc);

	elementsa.first().getElementsByTag("a").removeAttr("href");

	return elementsa;
}
function parseHTML2(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("xntx-wrapper bodyText ky-thu ky-thu-2").first();

    return elementsa;
}
function parseHTML3(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    return elementsa;
}
function parseHTML4(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("xntx-onemonth bodyText ky-thu ky-thu-2").first();

    elementsa.getElementsByTag("a").removeAttr("href");

    elementsa.getElementsByClass("bodyText").remove();
    elementsa.getElementsByClass("faq").remove();
    elementsa.getElementsByTag("h2").get(1).remove();
    elementsa.getElementsByTag("ol").remove();

    return elementsa;
}
function parseHTML5(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    return elementsa;
}
function parseHTML6(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    return elementsa;
}
function parseHTML7(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2").first();

    elementsa.getElementsByTag("a").removeAttr("href");

    var elementsaImage = elementsa.getElementsByClass("text-center").first().getElementsByTag("img");
    var tempSrc = elementsaImage.attr("src");
    tempSrc = "http://tracuu.tuvisomenh.com" + tempSrc;
    elementsaImage.attr("src", tempSrc);

    elementsa.getElementsByTag("h2").get(1).remove();
    elementsa.getElementsByTag("ol").remove();
    elementsa.getElementsByClass("faq").remove();

    return elementsa;
}
function parseHTML8(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    elementsa.first().getElementsByTag("a").removeAttr("href");

    return elementsa;
}
function parseHTML9(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2").first();

    elementsa.getElementsByTag("a").removeAttr("href");

    var elementsaImage = elementsa.getElementsByClass("text-center").first().getElementsByTag("img");
    var tempSrc = elementsaImage.attr("src");
    tempSrc = "http://tracuu.tuvisomenh.com" + tempSrc;
    elementsaImage.attr("src", tempSrc);

    elementsa.getElementsByTag("h2").get(1).remove();
    elementsa.getElementsByTag("ol").remove();
    elementsa.getElementsByClass("faq").remove();

    return elementsa;
}
function parseHTML10(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2").first();

    var elementsaImage = elementsa.getElementsByClass("text-center").first().getElementsByTag("img");
    var tempSrc = elementsaImage.attr("src");
    tempSrc = "http://tracuu.tuvisomenh.com" + tempSrc;
    elementsaImage.attr("src", tempSrc);

    elementsa.getElementsByTag("a").removeAttr("href");

    return elementsa;
}
function parseHTML11(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2").first();

    elementsa.getElementsByTag("a").removeAttr("href");

    var elementsaImage = elementsa.getElementsByClass("text-center").first().getElementsByTag("img");
    var tempSrc = elementsaImage.attr("src");
    tempSrc = "http://tracuu.tuvisomenh.com" + tempSrc;
    elementsaImage.attr("src", tempSrc);


    elementsa.getElementsByTag("h2").get(2).remove();
    elementsa.getElementsByClass("faq").remove();

    return elementsa;
}
function parseHTML12(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2").first();

    elementsa.getElementsByTag("a").removeAttr("href");

    var elementsaImage = elementsa.getElementsByClass("text-center").first().getElementsByTag("img");
    var tempSrc = elementsaImage.attr("src");
    tempSrc = "http://tracuu.tuvisomenh.com" + tempSrc;
    elementsaImage.attr("src", tempSrc);

    elementsa.getElementsByTag("h2").get(1).remove();
    elementsa.getElementsByClass("faq").remove();

    return elementsa;
}
function parseHTML13(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2").first();

    elementsa.getElementsByTag("a").removeAttr("href");

    var elementsaImage = elementsa.getElementsByClass("text-center").first().getElementsByTag("img");
    var tempSrc = elementsaImage.attr("src");
    tempSrc = "http://tracuu.tuvisomenh.com" + tempSrc;
    elementsaImage.attr("src", tempSrc);

    elementsa.getElementsByTag("h2").get(1).remove();
    elementsa.getElementsByClass("faq").remove();

    return elementsa;
}
function parseHTML14(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    return elementsa;
}
function parseHTML15(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    elementsa.first().getElementsByTag("a").removeAttr("href");

    return elementsa;
}
function parseHTML16(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    elementsa.first().getElementsByTag("a").removeAttr("href");

    return elementsa;
}
function parseHTML17(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    elementsa.first().getElementsByTag("a").removeAttr("href");

    var elementsaImage = elementsa.first().getElementsByTag("div").get(2).getElementsByTag("img");
    var tempSrc = elementsaImage.attr("src");
    tempSrc = "http://tracuu.tuvisomenh.com" + tempSrc;
    elementsaImage.attr("src", tempSrc);

    return elementsa;
}
function parseHTML18(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    elementsa.first().getElementsByTag("a").removeAttr("href");

    var elementsaImage = elementsa.first().getElementsByTag("div").get(2).getElementsByTag("img");
    var tempSrc = elementsaImage.attr("src");
    tempSrc = "http://tracuu.tuvisomenh.com" + tempSrc;
    elementsaImage.attr("src", tempSrc);

    return elementsa;
}
function parseHTML19(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2").first();

    var tempEle = elementsa.getElementsByClass("code-sample t-code-hightlight").first().getElementsByClass("prettyprint prettyprinted").first();
    tempEle.child(4).remove();

    elementsa.getElementsByTag("a").removeAttr("href");

    return elementsa;
}
function parseHTML20(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    return elementsa;
}
function parseHTML21(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    var elementsaImage = elementsa.first().getElementsByClass("text-center").first().getElementsByTag("img");
    var tempSrc = elementsaImage.attr("src");
    tempSrc = "http://tracuu.tuvisomenh.com" + tempSrc;
    elementsaImage.attr("src", tempSrc);

    elementsa.first().getElementsByTag("a").removeAttr("href");

    return elementsa;
}
function parseHTML22(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    elementsa.first().getElementsByTag("a").removeAttr("href");

    return elementsa;
}
function parseHTML23(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    return elementsa;
}
function parseHTML24(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("bodyText ky-thu ky-thu-2");

    return elementsa;
}
function parseHTML25(Util, content){
	var doc = Util.getJsoup(content);
    var elementsa = doc.getElementsByTag("body").first()
    .getElementById("wrapper").getElementById("layout-main-container")
    .getElementById("layout-main").getElementById("layout-content")
    .getElementById("content").child(1)
    .getElementsByClass("ky-thu ky-thu-2").first();

    elementsa.getElementsByTag("a").removeAttr("href");

    return elementsa;
}