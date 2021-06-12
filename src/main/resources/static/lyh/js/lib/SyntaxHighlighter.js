// SyntaxHighlighter 2.1.382
if(!window.SyntaxHighlighter)var SyntaxHighlighter=function(){var d={defaults:{"class-name":"","first-line":1,"pad-line-numbers":!0,highlight:null,"smart-tabs":!0,"tab-size":4,gutter:0,toolbar:0,collapse:!1,"auto-links":!0,light:!1,"wrap-lines":!0,"html-script":!1},config:{useScriptTags:!0,clipboardSwf:null,toolbarItemWidth:16,toolbarItemHeight:16,bloggerMode:!1,stripBrs:!1,tagName:"pre",strings:{expandSource:"show source",viewSource:"view source",copyToClipboard:"copy to clipboard",copyToClipboardConfirmation:"The code is in your clipboard now",print:"print",help:"?",alert:"SyntaxHighlighter\n\n",noBrush:"Can't find brush for: ",brushNotHtmlScript:"Brush wasn't configured for html-script option: ",aboutDialog:'<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>About SyntaxHighlighter</title></head><body style="font-family:Geneva,Arial,Helvetica,sans-serif;background-color:#fff;color:#000;font-size:1em;text-align:center;"><div style="text-align:center;margin-top:3em;"><div style="font-size:xx-large;">SyntaxHighlighter</div><div style="font-size:.75em;margin-bottom:4em;"><div>version 2.1.382 (June 24 2010)</div><div><a href="http://alexgorbatchev.com" target="_blank" style="color:#0099FF;text-decoration:none;">http://alexgorbatchev.com</a></div><div>If you like this script, please <a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2930402" style="color:#0099FF;text-decoration:none;">donate</a> to keep development active!</div></div><div>JavaScript code syntax highlighter.</div><div>Copyright 2004-2009 Alex Gorbatchev.</div></div></body></html>'},debug:!1},vars:{discoveredBrushes:null,spaceWidth:null,printFrame:null,highlighters:{}},brushes:{},regexLib:{multiLineCComments:/\/\*[\s\S]*?\*\//gm,singleLineCComments:/\/\/.*$/gm,singleLinePerlComments:/#.*$/gm,doubleQuotedString:/"([^\\"\n]|\\.)*"/g,singleQuotedString:/'([^\\'\n]|\\.)*'/g,multiLineDoubleQuotedString:/"([^\\"]|\\.)*"/g,multiLineSingleQuotedString:/'([^\\']|\\.)*'/g,xmlComments:/(&lt;|<)!--[\s\S]*?--(&gt;|>)/gm,url:/&lt;\w+:\/\/[\w-.\/?%&=@:;]*&gt;|\w+:\/\/[\w-.\/?%&=@:;]*/g,phpScriptTags:{left:/(&lt;|<)\?=?/g,right:/\?(&gt;|>)/g},aspScriptTags:{left:/(&lt;|<)%=?/g,right:/%(&gt;|>)/g},scriptScriptTags:{left:/(&lt;|<)\s*script.*?(&gt;|>)/gi,right:/(&lt;|<)\/\s*script\s*(&gt;|>)/gi}},toolbar:{create:function(a){var b=document.createElement("DIV"),c=d.toolbar.items;b.className="toolbar";for(var e in c){var g=new c[e](a),f=g.create();a.toolbarCommands[e]=g;null!=f&&("string"==typeof f&&(f=d.toolbar.createButton(f,a.id,e)),f.className+="item "+e,b.appendChild(f))}return b},createButton:function(a,b,c){var e=document.createElement("a"),g=e.style,f=d.config,i=f.toolbarItemWidth,f=f.toolbarItemHeight;e.href="#"+c;e.title=a;e.highlighterId=b;e.commandName=c;e.innerHTML=a;!1==isNaN(i)&&(g.width=i+"px");!1==isNaN(f)&&(g.height=f+"px");e.onclick=function(a){try{d.toolbar.executeCommand(this,a||window.event,this.highlighterId,this.commandName)}catch(b){d.utils.alert(b.message)}return!1};return e},executeCommand:function(a,b,c,e,g){var c=d.vars.highlighters[c],f;return null==c||null==(f=c.toolbarCommands[e])?null:f.execute(a,b,g)},items:{expandSource:function(a){this.create=function(){return!0!=a.getParam("collapse")?void 0:d.config.strings.expandSource};this.execute=function(b){var c=a.div;b.parentNode.removeChild(b);c.className=c.className.replace("collapsed","")}},viewSource:function(a){this.create=function(){return d.config.strings.viewSource};this.execute=function(){var b=d.utils.fixInputString(a.originalCode).replace(/</g,"&lt;"),c=d.utils.popup("","_blank",750,400,"location=0, resizable=1, menubar=0, scrollbars=1"),b=d.utils.unindent(b);c.document.write("<pre>"+b+"</pre>");c.document.close()}},copyToClipboard:function(a){var b,c=a.id;this.create=function(){function a(b){var c="",e;for(e in b)c+="<param name='"+e+"' value='"+b[e]+"'/>";return c}function g(a){var b="",c;for(c in a)b+=" "+c+"='"+a[c]+"'";return b}var f=d.config;if(null==f.clipboardSwf)return null;var i={width:f.toolbarItemWidth,height:f.toolbarItemHeight,id:c+"_clipboard",type:"application/x-shockwave-flash",title:d.config.strings.copyToClipboard},h={allowScriptAccess:"always",wmode:"transparent",flashVars:"highlighterId="+c,menu:"false"},f=f.clipboardSwf,i=/msie/i.test(navigator.userAgent)?"<object"+g({classid:"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000",codebase:"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0"})+g(i)+">"+a(h)+a({movie:f})+"</object>":"<embed"+g(i)+g(h)+g({src:f})+"/>";b=document.createElement("div");b.innerHTML=i;return b};this.execute=function(b,c,f){switch(f.command){case "get":if(b=d.utils.unindent(d.utils.fixInputString(a.originalCode).replace(/&lt;/g,"<").replace(/&gt;/g,">").replace(/&amp;/g,"&")),window.clipboardData)window.clipboardData.setData("text",b);else return d.utils.unindent(b);case "ok":d.utils.alert(d.config.strings.copyToClipboardConfirmation);break;case "error":d.utils.alert(f.message)}}},printSource:function(a){this.create=function(){return d.config.strings.print};this.execute=function(){var b=document.createElement("IFRAME"),c=null;null!=d.vars.printFrame&&document.body.removeChild(d.vars.printFrame);d.vars.printFrame=b;b.style.cssText="position:absolute;width:0px;height:0px;left:-500px;top:-500px;";document.body.appendChild(b);for(var e=c=b.contentWindow.document,g=window.document.getElementsByTagName("link"),f=0;f<g.length;f++)"stylesheet"==g[f].rel.toLowerCase()&&/shCore\.css$/.test(g[f].href)&&e.write('<link type="text/css" rel="stylesheet" href="'+g[f].href+'"></link>');c.write('<div class="'+a.div.className.replace("collapsed","")+' printing">'+a.div.innerHTML+"</div>");c.close();b.contentWindow.focus();b.contentWindow.print()}},about:function(){this.create=function(){return d.config.strings.help};this.execute=function(){var a=d.utils.popup("","_blank",500,250,"scrollbars=0"),b=a.document;b.write(d.config.strings.aboutDialog);b.close();a.focus()}}}},utils:{indexOf:function(a,b,c){for(c=Math.max(c||0,0);c<a.length;c++)if(a[c]==b)return c;return-1},guid:function(a){return a+Math.round(1E6*Math.random()).toString()},merge:function(a,b){var c={},e;for(e in a)c[e]=a[e];for(e in b)c[e]=b[e];return c},toBoolean:function(a){switch(a){case "true":return!0;case "false":return!1}return a},popup:function(a,b,c,e,d){d+=", left="+(screen.width-c)/2+", top="+(screen.height-e)/2+", width="+c+", height="+e;d=d.replace(/^,/,"");a=window.open(a,b,d);a.focus();return a},addEvent:function(a,b,c){a.attachEvent?(a["e"+b+c]=c,a[b+c]=function(){a["e"+b+c](window.event)},a.attachEvent("on"+b,a[b+c])):a.addEventListener(b,c,!1)},alert:function(a){alert(d.config.strings.alert+a)},findBrush:function(a,b){var c=d.vars.discoveredBrushes,e=null;if(null==c){var c={},g;for(g in d.brushes)if(e=d.brushes[g].aliases,null!=e){d.brushes[g].name=g.toLowerCase();for(var f=0;f<e.length;f++)c[e[f]]=g}d.vars.discoveredBrushes=c}e=d.brushes[c[a]];null==e&&!1!=b&&d.utils.alert(d.config.strings.noBrush+a);return e},eachLine:function(a,b){for(var c=a.split("\n"),e=0;e<c.length;e++)c[e]=b(c[e]);return c.join("\n")},trimFirstAndLastLines:function(a){return a.replace(/^[ ]*[\n]+|[\n]*[ ]*$/g,"")},parseParams:function(a){for(var b,c={},e=new XRegExp("^\\[(?<values>(.*?))\\]$"),d=new XRegExp("(?<name>[\\w-]+)\\s*:\\s*(?<value>[\\w-%#]+|\\[.*?\\]|\".*?\"|'.*?')\\s*;?","g");null!=(b=d.exec(a));){var f=b.value.replace(/^['"]|['"]$/g,"");null!=f&&e.test(f)&&(f=e.exec(f),f=0<f.values.length?f.values.split(/\s*,\s*/):[]);c[b.name]=f}return c},decorate:function(a,b){if(null==a||0==a.length||"\n"==a)return a;a=a.replace(/</g,"&lt;");a=a.replace(/ {2,}/g,function(a){for(var b="",d=0;d<a.length-1;d++)b+="&nbsp;";return b+" "});null!=b&&(a=d.utils.eachLine(a,function(a){if(0==a.length)return"";var d="",a=a.replace(/^(&nbsp;| )+/,function(a){d=a;return""});return 0==a.length?d:d+'<code class="'+b+'">'+a+"</code>"}));return a},padNumber:function(a,b){for(var c=a.toString();c.length<b;)c="0"+c;return c},measureSpace:function(){var a=document.createElement("div"),b;b=0;var c=document.body;b=d.utils.guid("measureSpace");a.innerHTML='<div class="syntaxhighlighter"><div class="lines"><div class="line"><div class="content"><span class="block"><span id="'+b+'">&nbsp;</span></span></div></div></div></div>';c.appendChild(a);b=document.getElementById(b);/opera/i.test(navigator.userAgent)?(b=window.getComputedStyle(b,null),b=parseInt(b.getPropertyValue("width"))):b=b.offsetWidth;c.removeChild(a);return b},processTabs:function(a,b){for(var c="",d=0;d<b;d++)c+=" ";return a.replace(/\t/g,c)},processSmartTabs:function(a,b){a.split("\n");for(var c="",e=0;50>e;e++)c+="                    ";return a=d.utils.eachLine(a,function(a){if(-1==a.indexOf("\t"))return a;for(var d=0;-1!=(d=a.indexOf("\t"));)a=a.substr(0,d)+c.substr(0,b-d%b)+a.substr(d+1,a.length);return a})},fixInputString:function(a){var b=/<br\s*\/?>|&lt;br\s*\/?&gt;/gi;!0==d.config.bloggerMode&&(a=a.replace(b,"\n"));!0==d.config.stripBrs&&(a=a.replace(b,""));return a},trim:function(a){return a.replace(/^\s+|\s+$/g,"")},unindent:function(a){for(var b=d.utils.fixInputString(a).split("\n"),c=/^\s*/,e=1E3,g=0;g<b.length&&0<e;g++){var f=b[g];if(0!=d.utils.trim(f).length){f=c.exec(f);if(null==f)return a;e=Math.min(f[0].length,e)}}if(0<e)for(g=0;g<b.length;g++)b[g]=b[g].substr(e);return b.join("\n")},matchesSortCallback:function(a,b){return a.index<b.index?-1:a.index>b.index?1:a.length<b.length?-1:a.length>b.length?1:0},getMatches:function(a,b){function c(a,b){return[new d.Match(a[0],a.index,b.css)]}for(var e=null,g=[],f=b.func?b.func:c;null!=(e=b.regex.exec(a));)g=g.concat(f(e,b));return g},processUrls:function(a){return a.replace(d.regexLib.url,function(a){var c="",d="";0==a.indexOf("&lt;")&&(d="&lt;",a=a.substring(4));a.indexOf("&gt;")==a.length-4&&(a=a.substring(0,a.length-4),c="&gt;");return d+'<a href="'+a+'">'+a+"</a>"+c})},getSyntaxHighlighterScriptTags:function(){for(var a=document.getElementsByTagName("script"),b=[],c=0;c<a.length;c++)"syntaxhighlighter"==a[c].type&&b.push(a[c]);return b},stripCData:function(a){var b=d.utils.trim(a),c=!1;0==b.indexOf("<![CDATA[")&&(b=b.substring(9),c=!0);b.indexOf("]]\>")==b.length-3&&(b=b.substring(0,b.length-3),c=!0);return c?b:a}},highlight:function(a,b){var c;if(b)c=[b];else{c=document.getElementsByTagName(d.config.tagName);for(var e=[],g=0;g<c.length;g++)e.push(c[g]);c=e}var f=null,e=d.config;e.useScriptTags&&(c=c.concat(d.utils.getSyntaxHighlighterScriptTags()));if(0!==c.length)for(g=0;g<c.length;g++){var i=c[g],h=d.utils.parseParams(i.className),j,l,h=d.utils.merge(a,h);j=h.brush;if(null!=j){if("true"==h["html-script"]||!0==d.defaults["html-script"])f=new d.HtmlScript(j),j="htmlscript";else if(f=d.utils.findBrush(j))j=f.name,f=new f;else continue;l=i.innerHTML;e.useScriptTags&&(l=d.utils.stripCData(l));h["brush-name"]=j;f.highlight(l,h);h=f.div;d.config.debug&&(h=document.createElement("textarea"),h.value=f.div.innerHTML,h.style.width="70em",h.style.height="30em");i.parentNode.replaceChild(h,i)}}},all:function(a){d.utils.addEvent(window,"load",function(){d.highlight(a)})},Match:function(a,b,c){this.value=a;this.index=b;this.length=a.length;this.css=c;this.brushName=null}};d.Match.prototype.toString=function(){return this.value};d.HtmlScript=function(a){function b(a,b){for(var c=0;c<a.length;c++)a[c].index+=b}var c=d.utils.findBrush(a),e,g=new d.brushes.Xml;null!=c&&(e=new c,this.xmlBrush=g,null==e.htmlScript?d.utils.alert(d.config.strings.brushNotHtmlScript+a):g.regexList.push({regex:e.htmlScript.code,func:function(a){for(var g=a.code,h=[],j=e.regexList,l=a.index+a.left.length,p=e.htmlScript,m,n=0;n<j.length;n++)m=d.utils.getMatches(g,j[n]),b(m,l),h=h.concat(m);null!=p.left&&null!=a.left&&(m=d.utils.getMatches(a.left,p.left),b(m,a.index),h=h.concat(m));null!=p.right&&null!=a.right&&(m=d.utils.getMatches(a.right,p.right),b(m,a.index+a[0].lastIndexOf(a.right)),h=h.concat(m));for(a=0;a<h.length;a++)h[a].brushName=c.name;return h}}))};d.HtmlScript.prototype.highlight=function(a,b){this.xmlBrush.highlight(a,b);this.div=this.xmlBrush.div};d.Highlighter=function(){};d.Highlighter.prototype={getParam:function(a,b){var c=this.params[a];return d.utils.toBoolean(null==c?b:c)},create:function(a){return document.createElement(a)},findMatches:function(a,b){var c=[];if(null!=a)for(var e=0;e<a.length;e++)"object"==typeof a[e]&&(c=c.concat(d.utils.getMatches(b,a[e])));return c.sort(d.utils.matchesSortCallback)},removeNestedMatches:function(){for(var a=this.matches,b=0;b<a.length;b++)if(null!==a[b])for(var c=a[b],d=c.index+c.length,g=b+1;g<a.length&&null!==a[b];g++){var f=a[g];if(null!==f)if(f.index>d)break;else f.index==c.index&&f.length>c.length?this.matches[b]=null:f.index>=c.index&&f.index<d&&(this.matches[g]=null)}},createDisplayLines:function(a){var b=a.split("\n"),c=parseInt(this.getParam("first-line")),e=this.getParam("pad-line-numbers"),g=this.getParam("highlight",[]),f=this.getParam("gutter"),a="";!0==e?e=(c+b.length-1).toString().length:!0==isNaN(e)&&(e=0);for(var i=0;i<b.length;i++){var h=b[i],j=/^(&nbsp;|\s)+/.exec(h),l="alt"+(0==i%2?1:2),p=d.utils.padNumber(c+i,e),m=-1!=d.utils.indexOf(g,(c+i).toString()),n=null;null!=j&&(n=j[0].toString(),h=h.substr(n.length));h=d.utils.trim(h);0==h.length&&(h="&nbsp;");m&&(l+=" highlighted");a+='<div class="line '+l+'"><table><tr>'+(f?'<td class="number"><code>'+p+"</code></td>":"")+'<td class="content">'+(null!=n?'<code class="spaces">'+n.replace(" ","&nbsp;")+"</code>":"")+h+"</td></tr></table></div>"}return a},processMatches:function(a,b){function c(a){return(a=a?a.brushName||i:i)?a+" ":""}for(var e=0,g="",f=d.utils.decorate,i=this.getParam("brush-name",""),h=0;h<b.length;h++){var j=b[h],l;null===j||0===j.length||(l=c(j),g+=f(a.substr(e,j.index-e),l+"plain")+f(j.value,l+j.css),e=j.index+j.length)}return g+=f(a.substr(e),c()+"plain")},highlight:function(a,b){var c=d.vars,e;this.params={};this.bar=this.code=this.lines=this.div=null;this.toolbarCommands={};this.id=d.utils.guid("highlighter_");c.highlighters[this.id]=this;null===a&&(a="");this.params=d.utils.merge(d.defaults,b||{});!0==this.getParam("light")&&(this.params.toolbar=this.params.gutter=!1);this.div=c=this.create("DIV");this.lines=this.create("DIV");this.lines.className="lines";className="syntaxhighlighter";c.id=this.id;this.getParam("collapse")&&(className+=" collapsed");!1==this.getParam("gutter")&&(className+=" nogutter");!1==this.getParam("wrap-lines")&&(this.lines.className+=" no-wrap");className+=" "+this.getParam("class-name");className+=" "+this.getParam("brush-name");c.className=className;this.originalCode=a;this.code=d.utils.trimFirstAndLastLines(a).replace(/\r/g," ");e=this.getParam("tab-size");this.code=!0==this.getParam("smart-tabs")?d.utils.processSmartTabs(this.code,e):d.utils.processTabs(this.code,e);this.code=d.utils.unindent(this.code);if(this.getParam("toolbar")){this.bar=this.create("DIV");this.bar.className="bar";this.bar.appendChild(d.toolbar.create(this));c.appendChild(this.bar);var g=this.bar,f=function(){g.className=g.className.replace("show","")};c.onmouseover=function(){f();g.className+=" show"};c.onmouseout=function(){f()}}c.appendChild(this.lines);this.matches=this.findMatches(this.regexList,this.code);this.removeNestedMatches();a=this.processMatches(this.code,this.matches);a=this.createDisplayLines(d.utils.trim(a));this.getParam("auto-links")&&(a=d.utils.processUrls(a));this.lines.innerHTML=a},getKeywords:function(a){a=a.replace(/^\s+|\s+$/g,"").replace(/\s+/g,"|");return"\\b(?:"+a+")\\b"},forHtmlScript:function(a){this.htmlScript={left:{regex:a.left,css:"script"},right:{regex:a.right,css:"script"},code:new XRegExp("(?<left>"+a.left.source+")(?<code>.*?)(?<right>"+a.right.source+")","sgi")}}};return d}();window.XRegExp||function(){var d=RegExp.prototype.exec,a=String.prototype.replace,b=/(?:[^\\([#\s.]+|\\(?!k<[\w$]+>|[pP]{[^}]+})[\S\s]?|\((?=\?(?!#|<[\w$]+>)))+|(\()(?:\?(?:(#)[^)]*\)|<([$\w]+)>))?|\\(?:k<([\w$]+)>|[pP]{([^}]+)})|(\[\^?)|([\S\s])/g,c=/^(?:\s+|#.*)+/,e=/^(?:[?*+]|{\d+(?:,\d*)?})/,g=/&&\[\^?/g,f=/]/g,i=void 0!==/()??/.exec("")[1],h={};XRegExp=function(j,l){if(j instanceof RegExp){if(void 0!==l)throw TypeError("can't supply flags when constructing one RegExp from another");return j.addFlags()}var l=l||"",i=-1<l.indexOf("s"),m=-1<l.indexOf("x"),n=!1,q=[],o=[],k,r;for(b.lastIndex=0;k=d.call(b,j);)if(k[2])e.test(j.slice(b.lastIndex))||o.push("(?:)");else if(k[1])q.push(k[3]||null),k[3]&&(n=!0),o.push("(");else if(k[4]){a:{for(r=0;r<q.length;r++)if(q[r]===k[4])break a;r=-1}o.push(-1<r?"\\"+(r+1)+(isNaN(j.charAt(b.lastIndex))?"":"(?:)"):k[0])}else k[5]?o.push(h.unicode?h.unicode.get(k[5],"P"===k[0].charAt(1)):k[0]):k[6]?"]"===j.charAt(b.lastIndex)?(o.push("["===k[6]?"(?!)":"[\\S\\s]"),b.lastIndex++):(r=XRegExp.matchRecursive("&&"+j.slice(k.index),g,f,"",{escapeChar:"\\"})[0],o.push(k[6]+r+"]"),b.lastIndex+=r.length+1):k[7]?i&&"."===k[7]?o.push("[\\S\\s]"):m&&c.test(k[7])?(k=d.call(c,j.slice(b.lastIndex-1))[0].length,e.test(j.slice(b.lastIndex-1+k))||o.push("(?:)"),b.lastIndex+=k-1):o.push(k[7]):o.push(k[0]);i=RegExp(o.join(""),a.call(l,/[sx]+/g,""));i._x={source:j,captureNames:n?q:null};return i};XRegExp.addPlugin=function(a,b){h[a]=b};RegExp.prototype.exec=function(b){var c=d.call(this,b),e;if(c){i&&1<c.length&&(b=RegExp("^"+this.source+"$(?!\\s)",this.getNativeFlags()),a.call(c[0],b,function(){for(e=1;e<arguments.length-2;e++)void 0===arguments[e]&&(c[e]=void 0)}));if(this._x&&this._x.captureNames)for(e=1;e<c.length;e++)(b=this._x.captureNames[e-1])&&(c[b]=c[e]);this.global&&this.lastIndex>c.index+c[0].length&&this.lastIndex--}return c}}();RegExp.prototype.getNativeFlags=function(){return(this.global?"g":"")+(this.ignoreCase?"i":"")+(this.multiline?"m":"")+(this.extended?"x":"")+(this.sticky?"y":"")};RegExp.prototype.addFlags=function(d){d=new XRegExp(this.source,(d||"")+this.getNativeFlags());this._x&&(d._x={source:this._x.source,captureNames:this._x.captureNames?this._x.captureNames.slice(0):null});return d};RegExp.prototype.call=function(d,a){return this.exec(a)};RegExp.prototype.apply=function(d,a){return this.exec(a[0])};XRegExp.cache=function(d,a){var b="/"+d+"/"+(a||"");return XRegExp.cache[b]||(XRegExp.cache[b]=new XRegExp(d,a))};XRegExp.escape=function(d){return d.replace(/[-[\]{}()*+?.\\^$|,#\s]/g,"\\$&")};XRegExp.matchRecursive=function(d,a,b,c,e){var e=e||{},g=e.escapeChar,e=e.valueNames,c=c||"",f=-1<c.indexOf("g"),i=-1<c.indexOf("i"),h=-1<c.indexOf("m"),j=-1<c.indexOf("y"),c=c.replace(/y/g,""),a=a instanceof RegExp?a.global?a:a.addFlags("g"):new XRegExp(a,"g"+c),b=b instanceof RegExp?b.global?b:b.addFlags("g"):new XRegExp(b,"g"+c),c=[],l=0,p=0,m=0,n=0,q,o,k;if(g){if(1<g.length)throw SyntaxError("can't supply more than one escape character");if(h)throw TypeError("can't supply escape character when using the multiline flag");k=XRegExp.escape(g);k=RegExp("^(?:"+k+"[\\S\\s]|(?:(?!"+a.source+"|"+b.source+")[^"+k+"])+)+",i?"i":"")}for(;;){a.lastIndex=b.lastIndex=m+(g?(k.exec(d.slice(m))||[""])[0].length:0);i=a.exec(d);h=b.exec(d);i&&h&&(i.index<=h.index?h=null:i=null);if(i||h)p=(i||h).index,m=(i?a:b).lastIndex;else if(!l)break;if(j&&!l&&p>n)break;if(i)l++||(q=p,o=m);else if(h&&l){if(!--l&&(e?(e[0]&&q>n&&c.push([e[0],d.slice(n,q),n,q]),e[1]&&c.push([e[1],d.slice(q,o),q,o]),e[2]&&c.push([e[2],d.slice(o,p),o,p]),e[3]&&c.push([e[3],d.slice(p,m),p,m])):c.push(d.slice(o,p)),n=m,!f))break}else throw a.lastIndex=b.lastIndex=0,Error("subject data contains unbalanced delimiters");p===m&&m++}f&&!j&&e&&e[0]&&d.length>n&&c.push([e[0],d.slice(n),n,d.length]);a.lastIndex=b.lastIndex=0;return c};SyntaxHighlighter.brushes.JScript=function(){this.regexList=[{regex:SyntaxHighlighter.regexLib.singleLineCComments,css:"comments"},{regex:SyntaxHighlighter.regexLib.multiLineCComments,css:"comments"},{regex:SyntaxHighlighter.regexLib.doubleQuotedString,css:"string"},{regex:SyntaxHighlighter.regexLib.singleQuotedString,css:"string"},{regex:/\s*#.*/gm,css:"preprocessor"},{regex:RegExp(this.getKeywords("break case catch continue default delete do else false  for function if in instanceof new null return super switch this throw true try typeof var while with"),"gm"),css:"keyword"}];this.forHtmlScript(SyntaxHighlighter.regexLib.scriptScriptTags)};SyntaxHighlighter.brushes.JScript.prototype=new SyntaxHighlighter.Highlighter;SyntaxHighlighter.brushes.JScript.aliases=["js","jscript","javascript"];
SyntaxHighlighter.all();
