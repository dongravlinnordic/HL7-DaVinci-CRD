!function(t){function n(r){if(e[r])return e[r].exports;var o=e[r]={i:r,l:!1,exports:{}};return t[r].call(o.exports,o,o.exports,n),o.l=!0,o.exports}var e={};n.m=t,n.c=e,n.d=function(t,e,r){n.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:r})},n.r=function(t){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},n.t=function(t,e){if(1&e&&(t=n(t)),8&e)return t;if(4&e&&"object"==typeof t&&t&&t.__esModule)return t;var r=Object.create(null);if(n.r(r),Object.defineProperty(r,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var o in t)n.d(r,o,function(n){return t[n]}.bind(null,o));return r},n.n=function(t){var e=t&&t.__esModule?function(){return t.default}:function(){return t};return n.d(e,"a",e),e},n.o=function(t,n){return Object.prototype.hasOwnProperty.call(t,n)},n.p="/",n(n.s=404)}({10:function(t){t.exports=function(t){try{return!!t()}catch(t){return!0}}},109:function(t,n,e){t.exports=e(51)("native-function-to-string",Function.toString)},11:function(t,n,e){t.exports=!e(10)(function(){return 7!=Object.defineProperty({},"a",{get:function(){return 7}}).a})},110:function(t,n,e){var r=e(111);t.exports=function(t,n){return new(r(t))(n)}},111:function(t,n,e){var r=e(9),o=e(74),i=e(5)("species");t.exports=function(t){var n;return o(t)&&("function"==typeof(n=t.constructor)&&(n===Array||o(n.prototype))&&(n=void 0),r(n)&&(null===(n=n[i])&&(n=void 0))),void 0===n?Array:n}},112:function(t,n,e){"use strict";var r=e(56);e(4)({target:"RegExp",proto:!0,forced:r!==/./.exec},{exec:r})},113:function(t,n,e){e(11)&&"g"!=/./g.flags&&e(14).f(RegExp.prototype,"flags",{configurable:!0,get:e(57)})},114:function(t){t.exports=Object.is||function(t,n){return t===n?0!==t||1/t==1/n:t!=t&&n!=n}},13:function(t,n,e){var r=e(8),o=e(19),i=e(34),u=e(41)("src"),c=e(109),a="toString",f=(""+c).split(a);e(24).inspectSource=function(t){return c.call(t)},(t.exports=function(t,n,e,c){var a="function"==typeof e;a&&(i(e,"name")||o(e,"name",n)),t[n]===e||(a&&(i(e,u)||o(e,u,t[n]?""+t[n]:f.join(n+""))),t===r?t[n]=e:c?t[n]?t[n]=e:o(t,n,e):(delete t[n],o(t,n,e)))})(Function.prototype,a,function(){return"function"==typeof this&&this[u]||c.call(this)})},14:function(t,n,e){var r=e(7),o=e(84),i=e(73),u=Object.defineProperty;n.f=e(11)?Object.defineProperty:function(t,n,e){if(r(t),n=i(n,!0),r(e),o)try{return u(t,n,e)}catch(t){}if("get"in e||"set"in e)throw TypeError("Accessors not supported!");return"value"in e&&(t[n]=e.value),t}},15:function(t){t.exports=function(t){if(null==t)throw TypeError("Can't call method on  "+t);return t}},16:function(t,n,e){"use strict";var r=e(54);({})[e(5)("toStringTag")]="z",e(13)(Object.prototype,"toString",function(){return"[object "+r(this)+"]"},!0)},19:function(t,n,e){var r=e(14),o=e(50);t.exports=e(11)?function(t,n,e){return r.f(t,n,o(1,e))}:function(t,n,e){return t[n]=e,t}},20:function(t,n,e){var r=e(36),o=Math.min;t.exports=function(t){return 0<t?o(r(t),9007199254740991):0}},24:function(t){var n=t.exports={version:"2.6.5"};"number"==typeof __e&&(__e=n)},25:function(t,n,e){var r=e(42);t.exports=function(t,n,e){return r(t),void 0===n?t:1===e?function(e){return t.call(n,e)}:2===e?function(e,r){return t.call(n,e,r)}:3===e?function(e,r,o){return t.call(n,e,r,o)}:function(){return t.apply(n,arguments)}}},26:function(t){var n={}.toString;t.exports=function(t){return n.call(t).slice(8,-1)}},33:function(t,n,e){"use strict";var r=e(4),o=e(35)(0),i=e(44)([].forEach,!0);r(r.P+r.F*!i,"Array",{forEach:function(t){return o(this,t,arguments[1])}})},34:function(t){var n={}.hasOwnProperty;t.exports=function(t,e){return n.call(t,e)}},35:function(t,n,e){var r=e(25),o=e(85),i=e(43),u=e(20),c=e(110);t.exports=function(t,n){var e=1==t,a=4==t,f=6==t,l=n||c;return function(n,c,s){for(var p,v,d=i(n),h=o(d),y=r(c,s,3),g=u(h.length),x=0,b=e?l(n,g):2==t?l(n,0):void 0;g>x;x++)if((5==t||f||x in h)&&(v=y(p=h[x],x,d),t))if(e)b[x]=v;else if(v)switch(t){case 3:return!0;case 5:return p;case 6:return x;case 2:b.push(p)}else if(a)return!1;return f?-1:3==t||a?a:b}}},36:function(t){var n=Math.ceil,e=Math.floor;t.exports=function(t){return isNaN(t=+t)?0:(0<t?e:n)(t)}},37:function(t,n,e){"use strict";e(113);var r=e(7),o=e(57),i=e(11),u="toString",c=/./[u],a=function(t){e(13)(RegExp.prototype,u,t,!0)};e(10)(function(){return"/a/b"!=c.call({source:"a",flags:"b"})})?a(function(){var t=r(this);return"/".concat(t.source,"/","flags"in t?t.flags:!i&&t instanceof RegExp?o.call(t):void 0)}):c.name!=u&&a(function(){return c.call(this)})},38:function(t,n,e){var r=Date.prototype,o="Invalid Date",i="toString",u=r[i],c=r.getTime;new Date(NaN)+""!=o&&e(13)(r,i,function(){var t=c.call(this);return t==t?u.call(this):o})},4:function(t,n,e){var r=e(8),o=e(24),i=e(19),u=e(13),c=e(25),a="prototype",f=function t(n,e,f){var l,s,p,v,d=n&t.F,h=n&t.G,y=n&t.P,g=n&t.B,x=h?r:n&t.S?r[e]||(r[e]={}):(r[e]||{})[a],b=h?o:o[e]||(o[e]={}),m=b[a]||(b[a]={});for(l in h&&(f=e),f)p=((s=!d&&x&&void 0!==x[l])?x:f)[l],v=g&&s?c(p,r):y&&"function"==typeof p?c(Function.call,p):p,x&&u(x,l,p,n&t.U),b[l]!=p&&i(b,l,v),y&&m[l]!=p&&(m[l]=p)};r.core=o,f.F=1,f.G=2,f.S=4,f.P=8,f.B=16,f.W=32,f.U=64,f.R=128,t.exports=f},404:function(t,n,e){"use strict";e.r(n);var r,o=e(33),i=(e.n(o),e(64)),u=(e.n(i),e(75)),c=(e.n(u),e(37)),a=(e.n(c),e(38)),f=(e.n(a),e(16)),l=(e.n(f),e(62)),s="c7ecff8d-5e91-48f2-b22e-f423c0c4c009",p=null,v=l.a.getUrlParameter("iss"),d=l.a.getUrlParameter("launch"),h="launch",y=Math.round(1e8*Math.random()).toString(),g=(window.location.protocol+"//"+window.location.host+window.location.pathname).replace("launch","index"),x=new XMLHttpRequest;x.open("GET",v+"/metadata?_format=json"),x.setRequestHeader("Content-Type","application/json"),x.setRequestHeader("Accept","application/json"),x.onload=function(){if(200!==x.status){var t="Conformance statement request failed. Returned status: "+x.status;return document.body.innerText=t,void console.error(t)}try{r=JSON.parse(x.responseText)}catch(t){var n="Unable to parse conformance statement.";return document.body.innerText=n,void console.error(n)}!function(t){var n,e;t.rest[0].security.extension.filter(function(t){return"http://fhir-registry.smarthealthit.org/StructureDefinition/oauth-uris"===t.url})[0].extension.forEach(function(t){"authorize"===t.url?n=t.valueUri:"token"===t.url&&(e=t.valueUri)}),sessionStorage[y]=JSON.stringify({clientId:s,secret:p,serviceUri:v,redirectUri:g,tokenUri:e}),console.log(encodeURIComponent(h)),window.location.href=n+"?response_type=code&client_id="+encodeURIComponent(s)+"&scope="+encodeURIComponent(h)+"&redirect_uri="+encodeURIComponent(g)+"&aud="+encodeURIComponent(v)+"&launch="+encodeURIComponent(d)+"&state="+y}(r)},x.send()},41:function(t){var n=0,e=Math.random();t.exports=function(t){return"Symbol(".concat(void 0===t?"":t,")_",(++n+e).toString(36))}},42:function(t){t.exports=function(t){if("function"!=typeof t)throw TypeError(t+" is not a function!");return t}},43:function(t,n,e){var r=e(15);t.exports=function(t){return Object(r(t))}},44:function(t,n,e){"use strict";var r=e(10);t.exports=function(t,n){return!!t&&r(function(){n?t.call(null,function(){},1):t.call(null)})}},5:function(t,n,e){var r=e(51)("wks"),o=e(41),i=e(8).Symbol,u="function"==typeof i;(t.exports=function(t){return r[t]||(r[t]=u&&i[t]||(u?i:o)("Symbol."+t))}).store=r},50:function(t){t.exports=function(t,n){return{enumerable:!(1&t),configurable:!(2&t),writable:!(4&t),value:n}}},51:function(t,n,e){var r=e(24),o=e(8),i="__core-js_shared__",u=o[i]||(o[i]={});(t.exports=function(t,n){return u[t]||(u[t]=void 0===n?{}:n)})("versions",[]).push({version:r.version,mode:e(52)?"pure":"global",copyright:"© 2019 Denis Pushkarev (zloirock.ru)"})},52:function(t){t.exports=!1},53:function(t,n,e){"use strict";function r(t){return(r="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t})(t)}var o=e(54),i=RegExp.prototype.exec;t.exports=function(t,n){var e=t.exec;if("function"==typeof e){var u=e.call(t,n);if("object"!==r(u))throw new TypeError("RegExp exec method returned something other than an Object or null");return u}if("RegExp"!==o(t))throw new TypeError("RegExp#exec called on incompatible receiver");return i.call(t,n)}},54:function(t,n,e){var r=e(26),o=e(5)("toStringTag"),i="Arguments"==r(function(){return arguments}());t.exports=function(t){var n,e,u;return void 0===t?"Undefined":null===t?"Null":"string"==typeof(e=function(t,n){try{return t[n]}catch(t){}}(n=Object(t),o))?e:i?r(n):"Object"==(u=r(n))&&"function"==typeof n.callee?"Arguments":u}},55:function(t,n,e){"use strict";e(112);var r=e(13),o=e(19),i=e(10),u=e(15),c=e(5),a=e(56),f=c("species"),l=!i(function(){var t=/./;return t.exec=function(){var t=[];return t.groups={a:"7"},t},"7"!=="".replace(t,"$<a>")}),s=function(){var t=/(?:)/,n=t.exec;t.exec=function(){return n.apply(this,arguments)};var e="ab".split(t);return 2===e.length&&"a"===e[0]&&"b"===e[1]}();t.exports=function(t,n,e){var p=c(t),v=!i(function(){var n={};return n[p]=function(){return 7},7!=""[t](n)}),d=v?!i(function(){var n=!1,e=/a/;return e.exec=function(){return n=!0,null},"split"===t&&(e.constructor={},e.constructor[f]=function(){return e}),e[p](""),!n}):void 0;if(!v||!d||"replace"===t&&!l||"split"===t&&!s){var h=/./[p],y=e(u,p,""[t],function(t,n,e,r,o){return n.exec===a?v&&!o?{done:!0,value:h.call(n,e,r)}:{done:!0,value:t.call(e,n,r)}:{done:!1}}),g=y[0],x=y[1];r(String.prototype,t,g),o(RegExp.prototype,p,2==n?function(t,n){return x.call(t,this,n)}:function(t){return x.call(t,this)})}}},56:function(t,n,e){"use strict";var r=e(57),o=RegExp.prototype.exec,i=String.prototype.replace,u=o,c="lastIndex",a=function(){var t=/a/,n=/b*/g;return o.call(t,"a"),o.call(n,"a"),0!==t[c]||0!==n[c]}(),f=void 0!==/()??/.exec("")[1];(a||f)&&(u=function(t){var n,e,u,l,s=this;return f&&(e=new RegExp("^"+s.source+"$(?!\\s)",r.call(s))),a&&(n=s[c]),u=o.call(s,t),a&&u&&(s[c]=s.global?u.index+u[0].length:n),f&&u&&1<u.length&&i.call(u[0],e,function(){for(l=1;l<arguments.length-2;l++)void 0===arguments[l]&&(u[l]=void 0)}),u}),t.exports=u},57:function(t,n,e){"use strict";var r=e(7);t.exports=function(){var t=r(this),n="";return t.global&&(n+="g"),t.ignoreCase&&(n+="i"),t.multiline&&(n+="m"),t.unicode&&(n+="u"),t.sticky&&(n+="y"),n}},62:function(t,n,e){"use strict";var r=e(75),o=(e.n(r),e(87)),i=(e.n(o),e(90)),u=(e.n(i),{getUrlParameter:function(t){for(var n,e=window.location.search.substring(1).split("&"),r=0;r<e.length;r++)if((n=e[r].split("="))[0]===t){var o=n[1].replace(/\+/g,"%20");return decodeURIComponent(o)}}});n.a=u},64:function(t,n,e){"use strict";var r=e(4),o=e(35)(2);r(r.P+r.F*!e(44)([].filter,!0),"Array",{filter:function(t){return o(this,t,arguments[1])}})},7:function(t,n,e){var r=e(9);t.exports=function(t){if(!r(t))throw TypeError(t+" is not an object!");return t}},72:function(t,n,e){var r=e(9),o=e(8).document,i=r(o)&&r(o.createElement);t.exports=function(t){return i?o.createElement(t):{}}},73:function(t,n,e){var r=e(9);t.exports=function(t,n){if(!r(t))return t;var e,o;if(n&&"function"==typeof(e=t.toString)&&!r(o=e.call(t)))return o;if("function"==typeof(e=t.valueOf)&&!r(o=e.call(t)))return o;if(!n&&"function"==typeof(e=t.toString)&&!r(o=e.call(t)))return o;throw TypeError("Can't convert object to primitive value")}},74:function(t,n,e){var r=e(26);t.exports=Array.isArray||function(t){return"Array"==r(t)}},75:function(t,n,e){"use strict";var r=e(7),o=e(43),i=e(20),u=e(36),c=e(76),a=e(53),f=Math.max,l=Math.min,s=Math.floor,p=/\$([$&`']|\d\d?|<[^>]*>)/g,v=/\$([$&`']|\d\d?)/g,d=function(t){return void 0===t?t:t+""};e(55)("replace",2,function(t,n,e,h){function y(t,n,r,i,u,c){var a=r+t.length,f=i.length,l=v;return void 0!==u&&(u=o(u),l=p),e.call(c,l,function(e,o){var c;switch(o.charAt(0)){case"$":return"$";case"&":return t;case"`":return n.slice(0,r);case"'":return n.slice(a);case"<":c=u[o.slice(1,-1)];break;default:var l=+o;if(0==l)return e;if(l>f){var p=s(l/10);return 0===p?e:p<=f?void 0===i[p-1]?o.charAt(1):i[p-1]+o.charAt(1):e}c=i[l-1]}return void 0===c?"":c})}return[function(r,o){var i=t(this),u=null==r?void 0:r[n];return void 0===u?e.call(i+"",r,o):u.call(r,i,o)},function(t,n){var o=h(e,t,this,n);if(o.done)return o.value;var s=r(t),p=this+"",v="function"==typeof n;v||(n+="");var g=s.global;if(g){var x=s.unicode;s.lastIndex=0}for(var b,m=[];null!==(b=a(s,p))&&(m.push(b),g);){""==b[0]+""&&(s.lastIndex=c(p,i(s.lastIndex),x))}for(var S="",w=0,j=0;j<m.length;j++){for(var E=(b=m[j])[0]+"",R=f(l(u(b.index),p.length),0),O=[],_=1;_<b.length;_++)O.push(d(b[_]));var I=b.groups;if(v){var M=[E].concat(O,R,p);void 0!==I&&M.push(I);var U=n.apply(void 0,M)+""}else U=y(E,p,R,O,I,n);R>=w&&(S+=p.slice(w,R)+U,w=R+E.length)}return S+p.slice(w)}]})},76:function(t,n,e){"use strict";var r=e(86)(!0);t.exports=function(t,n,e){return n+(e?r(t,n).length:1)}},8:function(t){var n=t.exports="undefined"!=typeof window&&window.Math==Math?window:"undefined"!=typeof self&&self.Math==Math?self:Function("return this")();"number"==typeof __g&&(__g=n)},84:function(t,n,e){t.exports=!e(11)&&!e(10)(function(){return 7!=Object.defineProperty(e(72)("div"),"a",{get:function(){return 7}}).a})},85:function(t,n,e){var r=e(26);t.exports=Object("z").propertyIsEnumerable(0)?Object:function(t){return"String"==r(t)?t.split(""):Object(t)}},86:function(t,n,e){var r=e(36),o=e(15);t.exports=function(t){return function(n,e){var i,u,c=o(n)+"",a=r(e),f=c.length;return 0>a||a>=f?t?"":void 0:55296>(i=c.charCodeAt(a))||56319<i||a+1===f||56320>(u=c.charCodeAt(a+1))||57343<u?t?c.charAt(a):i:t?c.slice(a,a+2):u-56320+(i-55296<<10)+65536}}},87:function(t,n,e){"use strict";var r=e(88),o=e(7),i=e(89),u=e(76),c=e(20),a=e(53),f=e(56),l=e(10),s=Math.min,p=[].push,v="split",d="length",h="lastIndex",y=4294967295,g=!l(function(){RegExp(y,"y")});e(55)("split",2,function(t,n,e,l){var x;return x="c"=="abbc"[v](/(b)*/)[1]||4!="test"[v](/(?:)/,-1)[d]||2!="ab"[v](/(?:ab)*/)[d]||4!="."[v](/(.?)(.?)/)[d]||1<"."[v](/()()/)[d]||""[v](/.?/)[d]?function(t,n){var o=this+"";if(void 0===t&&0===n)return[];if(!r(t))return e.call(o,t,n);for(var i,u,c,a=[],l=(t.ignoreCase?"i":"")+(t.multiline?"m":"")+(t.unicode?"u":"")+(t.sticky?"y":""),s=0,v=void 0===n?y:n>>>0,g=new RegExp(t.source,l+"g");(i=f.call(g,o))&&!((u=g[h])>s&&(a.push(o.slice(s,i.index)),1<i[d]&&i.index<o[d]&&p.apply(a,i.slice(1)),c=i[0][d],s=u,a[d]>=v));)g[h]===i.index&&g[h]++;return s===o[d]?(c||!g.test(""))&&a.push(""):a.push(o.slice(s)),a[d]>v?a.slice(0,v):a}:"0"[v](void 0,0)[d]?function(t,n){return void 0===t&&0===n?[]:e.call(this,t,n)}:e,[function(e,r){var o=t(this),i=null==e?void 0:e[n];return void 0===i?x.call(o+"",e,r):i.call(e,o,r)},function(t,n){var r=l(x,t,this,n,x!==e);if(r.done)return r.value;var f=o(t),p=this+"",v=i(f,RegExp),d=f.unicode,h=(f.ignoreCase?"i":"")+(f.multiline?"m":"")+(f.unicode?"u":"")+(g?"y":"g"),b=new v(g?f:"^(?:"+f.source+")",h),m=void 0===n?y:n>>>0;if(0==m)return[];if(0===p.length)return null===a(b,p)?[p]:[];for(var S=0,w=0,j=[];w<p.length;){b.lastIndex=g?w:0;var E,R=a(b,g?p:p.slice(w));if(null===R||(E=s(c(b.lastIndex+(g?0:w)),p.length))===S)w=u(p,w,d);else{if(j.push(p.slice(S,w)),j.length===m)return j;for(var O=1;O<=R.length-1;O++)if(j.push(R[O]),j.length===m)return j;w=S=E}}return j.push(p.slice(S)),j}]})},88:function(t,n,e){var r=e(9),o=e(26),i=e(5)("match");t.exports=function(t){var n;return r(t)&&(void 0===(n=t[i])?"RegExp"==o(t):!!n)}},89:function(t,n,e){var r=e(7),o=e(42),i=e(5)("species");t.exports=function(t,n){var e,u=r(t).constructor;return void 0===u||null==(e=r(u)[i])?n:o(e)}},9:function(t){function n(t){return(n="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t})(t)}t.exports=function(t){return"object"===n(t)?null!==t:"function"==typeof t}},90:function(t,n,e){"use strict";var r=e(7),o=e(114),i=e(53);e(55)("search",1,function(t,n,e,u){return[function(e){var r=t(this),o=null==e?void 0:e[n];return void 0===o?new RegExp(e)[n](r+""):o.call(e,r)},function(t){var n=u(e,t,this);if(n.done)return n.value;var c=r(t),a=this+"",f=c.lastIndex;o(f,0)||(c.lastIndex=0);var l=i(c,a);return o(c.lastIndex,f)||(c.lastIndex=f),null===l?-1:l.index}]})}});