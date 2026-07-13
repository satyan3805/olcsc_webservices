/*
 * Litle & Co. Pay Page Java Script API Version: 2.4
 * Copyright © 2003-2015, Litle & Co. ALL RIGHTS RESERVED.
 * Includes litle-api2.js
 * http://www.litle.com/
 */
var LitlePayPage=function(){var a={modulus:"bc637dd74ba76507dad5af1c7ad6f97dbef5298c3b9f74caea7301347db7b4a8c37f26491863100667246fd45071f3346bf62239f9b117d06fb67861b66ad0d158beeddd2f6f28be93d846f4c8f9ba1bd7e8f186f36cab0e432f22b3d732c221a9ff00a9bfacb88b24503e2695fd7237835d4936477b21289478906a49b164f52503c20eb29f11fcbda2af94deb9a0bfde5a4589276897436315c5d664d60bf10650164f509283aed39747ad5d6cb2bbe54e1b42306e5db37dfd42dcbfcc689e0ddfe3bc9cb22ae7018e5a4a1ff39813584ac7bd6d6d65ca763f0a672da454081ea20e8b1d403316d80b9353ba396bea8962b1a7d2f775c76612d857c1f7594f",exponent:"10001",keyId:"1"};
return{sendToLitle:function(d,F,u,L,G,M){var o=new Date().getTime();var l=0;var B=null;setTimeout(function(){if(B!=null){if(B.response=="870"){s(B);
return}else{k(B);return}}l=new Date().getTime()-o;if(M&&l>M){z()}else{setTimeout(arguments.callee,10)}},10);F.paypageRegistrationId.value="";
F.bin.value="";var I=F.accountNum.value;I=H(I);var y=(jQuery(F.cvv2).length>0);if(y){var P=F.cvv2.value;P=H(P)}if(d.pciNonSensitive===undefined){d.pciNonSensitive=false
}var E=e(I,d.pciNonSensitive);if(E!="870"){return q(E)}if(y){E=N(P);if(E!="870"){return q(E)}}var j=new RSAKey();try{var t=j.setPublic(a.modulus,a.exponent);
var f=j.encrypt(I);if(y){var i=j.encrypt(P)}}catch(J){return q("875")}if(f){var p=hex2b64(f);var x=encodeURIComponent(p);
if(y){var A=hex2b64(i);var D=encodeURIComponent(A)}try{n("paypageId",d.paypageId,C,c,K);n("reportGroup",d.reportGroup,C,c);
n("id",d.id,C,c)}catch(J){return q("889",J)}var O=encodeURIComponent(d.paypageId);var r=encodeURIComponent(d.reportGroup);
var w=encodeURIComponent(d.orderId);var g=encodeURIComponent(d.id);var h=encodeURIComponent(d.pciNonSensitive);if(y){jQuery.getJSON(d.url+"/LitlePayPage/paypage?paypageId="+O+"&reportGroup="+r+"&id="+g+"&orderId="+w+"&encryptedAccount="+x+"&publicKeyId="+a.keyId+"&encryptedCvv="+D+"&pciNonSensitive="+h+"&jsoncallback=?",function(Q){B=Q
})}else{jQuery.getJSON(d.url+"/LitlePayPage/paypage?paypageId="+O+"&reportGroup="+r+"&id="+g+"&orderId="+w+"&encryptedAccount="+x+"&publicKeyId="+a.keyId+"&pciNonSensitive="+h+"&jsoncallback=?",function(Q){B=Q
})}}else{return javaScriptError("875")}function s(S){F.accountNum.value=m(I);var T=H(I);S.firstSix=T.substring(0,6);S.lastFour=T.substring(T.length-4,T.length);
if(F.extraAccountNums){for(var R in F.extraAccountNums){var Q=F.extraAccountNums[R];Q.value=m(H(Q.value))}}if(y){F.cvv2.value="000"
}F.paypageRegistrationId.value=S.paypageRegistrationId;F.bin.value=S.bin;u(S)}function k(Q){L(Q)}function z(){G()}function m(Q){if(!Q){return Q
}Q=Q.substring(0,Q.length-4).replace(/./g,"X").concat(Q.substring(Q.length-4));return Q}function b(Q){Q=(Q+"").split("").reverse();
if(!Q.length){return false}var S=0,R;for(R=0;R<Q.length;R++){Q[R]=parseInt(Q[R]);S+=R%2?2*Q[R]-(Q[R]>4?9:0):Q[R]}return(S%10)==0
}function e(T,S){if(T=="6011010000000003"){return"875"}if(T=="375001000000005"){M=-1;var R=new Date();var Q=null;do{Q=new Date()
}while(Q-R<10)}if(T=="4457010200000007"){return"889"}if(T.length<13){return"872"}else{if(T.length>19){return"873"}else{if(!T.match(/^[0-9]{13,19}$/)){return"874"
}else{if(!S&&!b(T)){return"871"}else{return"870"}}}}}function N(Q){if(Q.length<3){return"882"}else{if(Q.length>4){return"883"
}else{if(!Q.match(/^\d\d\d\d?$/)){return"881"}else{return"870"}}}}function n(){var R=arguments[0];var S=arguments[1];for(var Q=2;
Q<arguments.length;Q++){arguments[Q](R,S)}}function C(Q,R){if(R.length==0){throw"Parameter "+Q+" is required"}}function c(Q,R){if(R.length>1024){throw"Parameter "+Q+" is too long.  Length is "+R.length
}}function K(Q,R){if(!R.match(/^[0-9a-zA-Z]+$/)){throw"Parameter "+Q+" with value "+R+" is not alphanumeric"}}function q(U,T){var V={response:null,message:null};
var R={"870":"Success","871":"Account number not mod10","872":"Account number too short","873":"Account number too long","874":"Account number not numeric","875":"Unable to encrypt field","876":"Account number invalid","881":"Card validation num not numeric","882":"Card validation num too short","883":"Card validation num too long","889":"Failure"};
function Q(W){return W<10?"0"+W:W}function S(W){return W.getUTCFullYear()+"-"+Q(W.getUTCMonth()+1)+"-"+Q(W.getUTCDate())+"T"+Q(W.getUTCHours())+":"+Q(W.getUTCMinutes())+":"+Q(W.getUTCSeconds())
}V.response=U;if(T==undefined){V.message=R[U]}else{V.message=T}V.responseTime=S(new Date());V.reportGroup=d.reportGroup;V.id=d.id;
V.orderId=d.orderId;B=V}function H(Q){return Q.replace(/[ -]/gi,"")}}}};function rng_get_bytes(l){var d=0;var b;var p;var o;
var j;var k;try{if(window.crypto&&window.crypto.getRandomValues){b=new Int8Array(l.length);window.crypto.getRandomValues(b);
for(p=0;p<b.length;++p){while(b[p]==0){j=new Int8Array(1);window.crypto.getRandomValues(j);b[p]=j[0]}l[d++]=b[p]}}else{if(window.msCrypto&&window.msCrypto.getRandomValues){b=new Int8Array(l.length);
window.msCrypto.getRandomValues(b);for(p=0;p<b.length;++p){while(b[p]==0){j=new Int8Array(1);window.msCrypto.getRandomValues(j);
b[p]=j[0]}l[d++]=b[p]}}else{o=sjcl.random.randomWords((l.length/4)+1,0);var h=0;while(d<o.length){var f=o[d++];var m=f>>0&255;
var n=f>>8&255;var a=f>>16&255;var g=f>>24&255;while(m==0||n==0||a==0||g==0){k=new Array();k=sjcl.random.randomWords(1,0);
f=k[0];m=f>>0&255;n=f>>8&255;a=f>>16&255;g=f>>24&255}if(h<l.length){l[h++]=m}if(h<l.length){l[h++]=n}if(h<l.length){l[h++]=a
}if(h<l.length){l[h++]=g}}}}}catch(i){for(p=0;p<l.length;++p){var c=Math.floor((Math.random()*255)+1);while(c==0){c=Math.floor((Math.random()*255)+1)
}l[d++]=c}}return 1}"use strict";var sjcl={cipher:{},hash:{},keyexchange:{},mode:{},misc:{},codec:{},exception:{corrupt:function(a){this.toString=function(){return"CORRUPT: "+this.message
};this.message=a},invalid:function(a){this.toString=function(){return"INVALID: "+this.message};this.message=a},bug:function(a){this.toString=function(){return"BUG: "+this.message
};this.message=a},notReady:function(a){this.toString=function(){return"NOT READY: "+this.message};this.message=a}}};sjcl.cipher.aes=function(h){if(!this._tables[0][0][0]){this._precompute()
}var d,c,e,g,l,f=this._tables[0][4],k=this._tables[1],a=h.length,b=1;if(a!==4&&a!==6&&a!==8){throw new sjcl.exception.invalid("invalid aes key size")
}this._key=[g=h.slice(0),l=[]];for(d=a;d<4*a+28;d++){e=g[d-1];if(d%a===0||(a===8&&d%a===4)){e=f[e>>>24]<<24^f[e>>16&255]<<16^f[e>>8&255]<<8^f[e&255];
if(d%a===0){e=e<<8^e>>>24^b<<24;b=b<<1^(b>>7)*283}}g[d]=g[d-a]^e}for(c=0;d;c++,d--){e=g[c&3?d:d-4];if(d<=4||c<4){l[c]=e}else{l[c]=k[0][f[e>>>24]]^k[1][f[e>>16&255]]^k[2][f[e>>8&255]]^k[3][f[e&255]]
}}};sjcl.cipher.aes.prototype={encrypt:function(a){return this._crypt(a,0)},decrypt:function(a){return this._crypt(a,1)},_tables:[[[],[],[],[],[]],[[],[],[],[],[]]],_precompute:function(){var j=this._tables[0],q=this._tables[1],h=j[4],n=q[4],g,l,f,k=[],c=[],b,p,m,o,e,a;
for(g=0;g<256;g++){c[(k[g]=g<<1^(g>>7)*283)^g]=g}for(l=f=0;!h[l];l^=b||1,f=c[f]||1){o=f^f<<1^f<<2^f<<3^f<<4;o=o>>8^o&255^99;
h[l]=o;n[o]=l;m=k[p=k[b=k[l]]];a=m*16843009^p*65537^b*257^l*16843008;e=k[o]*257^o*16843008;for(g=0;g<4;g++){j[g][l]=e=e<<24^e>>>8;
q[g][o]=a=a<<24^a>>>8}}for(g=0;g<5;g++){j[g]=j[g].slice(0);q[g]=q[g].slice(0)}},_crypt:function(k,n){if(k.length!==4){throw new sjcl.exception.invalid("invalid aes block size")
}var z=this._key[n],w=k[0]^z[0],u=k[n?3:1]^z[1],t=k[2]^z[2],s=k[n?1:3]^z[3],x,e,m,y=z.length/4-2,p,o=4,q=[0,0,0,0],r=this._tables[n],j=r[0],h=r[1],g=r[2],f=r[3],l=r[4];
for(p=0;p<y;p++){x=j[w>>>24]^h[u>>16&255]^g[t>>8&255]^f[s&255]^z[o];e=j[u>>>24]^h[t>>16&255]^g[s>>8&255]^f[w&255]^z[o+1];
m=j[t>>>24]^h[s>>16&255]^g[w>>8&255]^f[u&255]^z[o+2];s=j[s>>>24]^h[w>>16&255]^g[u>>8&255]^f[t&255]^z[o+3];o+=4;w=x;u=e;t=m
}for(p=0;p<4;p++){q[n?3&-p:p]=l[w>>>24]<<24^l[u>>16&255]<<16^l[t>>8&255]<<8^l[s&255]^z[o++];x=w;w=u;u=t;t=s;s=x}return q}};
sjcl.bitArray={bitSlice:function(b,c,d){b=sjcl.bitArray._shiftRight(b.slice(c/32),32-(c&31)).slice(1);return(d===undefined)?b:sjcl.bitArray.clamp(b,d-c)
},extract:function(c,d,f){var b,e=Math.floor((-d-f)&31);if((d+f-1^d)&-32){b=(c[d/32|0]<<(32-e))^(c[d/32+1|0]>>>e)}else{b=c[d/32|0]>>>e
}return b&((1<<f)-1)},concat:function(c,a){if(c.length===0||a.length===0){return c.concat(a)}var d=c[c.length-1],b=sjcl.bitArray.getPartial(d);
if(b===32){return c.concat(a)}else{return sjcl.bitArray._shiftRight(a,b,d|0,c.slice(0,c.length-1))}},bitLength:function(d){var c=d.length,b;
if(c===0){return 0}b=d[c-1];return(c-1)*32+sjcl.bitArray.getPartial(b)},clamp:function(d,b){if(d.length*32<b){return d}d=d.slice(0,Math.ceil(b/32));
var c=d.length;b=b&31;if(c>0&&b){d[c-1]=sjcl.bitArray.partial(b,d[c-1]&2147483648>>(b-1),1)}return d},partial:function(b,a,c){if(b===32){return a
}return(c?a|0:a<<(32-b))+b*1099511627776},getPartial:function(a){return Math.round(a/1099511627776)||32},equal:function(e,d){if(sjcl.bitArray.bitLength(e)!==sjcl.bitArray.bitLength(d)){return false
}var c=0,f;for(f=0;f<e.length;f++){c|=e[f]^d[f]}return(c===0)},_shiftRight:function(d,c,h,f){var g,b=0,e;if(f===undefined){f=[]
}for(;c>=32;c-=32){f.push(h);h=0}if(c===0){return f.concat(d)}for(g=0;g<d.length;g++){f.push(h|d[g]>>>c);h=d[g]<<(32-c)}b=d.length?d[d.length-1]:0;
e=sjcl.bitArray.getPartial(b);f.push(sjcl.bitArray.partial(c+e&31,(c+e>32)?h:f.pop(),1));return f},_xor4:function(a,b){return[a[0]^b[0],a[1]^b[1],a[2]^b[2],a[3]^b[3]]
},byteswapM:function(c){var e,d,b=65280;for(e=0;e<c.length;++e){d=c[e];c[e]=(d>>>24)|((d>>>8)&b)|((d&b)<<8)|(d<<24)}return c
}};sjcl.codec.utf8String={fromBits:function(a){var b="",e=sjcl.bitArray.bitLength(a),d,c;for(d=0;d<e/8;d++){if((d&3)===0){c=a[d/4]
}b+=String.fromCharCode(c>>>24);c<<=8}return decodeURIComponent(escape(b))},toBits:function(d){d=unescape(encodeURIComponent(d));
var a=[],c,b=0;for(c=0;c<d.length;c++){b=b<<8|d.charCodeAt(c);if((c&3)===3){a.push(b);b=0}}if(c&3){a.push(sjcl.bitArray.partial(8*(c&3),b))
}return a}};sjcl.hash.sha256=function(a){if(!this._key[0]){this._precompute()}if(a){this._h=a._h.slice(0);this._buffer=a._buffer.slice(0);
this._length=a._length}else{this.reset()}};sjcl.hash.sha256.hash=function(a){return(new sjcl.hash.sha256()).update(a).finalize()
};sjcl.hash.sha256.prototype={blockSize:512,reset:function(){this._h=this._init.slice(0);this._buffer=[];this._length=0;return this
},update:function(f){if(typeof f==="string"){f=sjcl.codec.utf8String.toBits(f)}var e,a=this._buffer=sjcl.bitArray.concat(this._buffer,f),d=this._length,c=this._length=d+sjcl.bitArray.bitLength(f);
for(e=512+d&-512;e<=c;e+=512){this._block(a.splice(0,16))}return this},finalize:function(){var c,a=this._buffer,d=this._h;
a=sjcl.bitArray.concat(a,[sjcl.bitArray.partial(1,1)]);for(c=a.length+2;c&15;c++){a.push(0)}a.push(Math.floor(this._length/4294967296));
a.push(this._length|0);while(a.length){this._block(a.splice(0,16))}this.reset();return d},_init:[],_key:[],_precompute:function(){var d=0,c=2,b;
function a(e){return(e-Math.floor(e))*4294967296|0}outer:for(;d<64;c++){for(b=2;b*b<=c;b++){if(c%b===0){continue outer}}if(d<8){this._init[d]=a(Math.pow(c,1/2))
}this._key[d]=a(Math.pow(c,1/3));d++}},_block:function(q){var e,f,t,s,u=q.slice(0),j=this._h,c=this._key,r=j[0],p=j[1],o=j[2],n=j[3],m=j[4],l=j[5],g=j[6],d=j[7];
for(e=0;e<64;e++){if(e<16){f=u[e]}else{t=u[(e+1)&15];s=u[(e+14)&15];f=u[e&15]=((t>>>7^t>>>18^t>>>3^t<<25^t<<14)+(s>>>17^s>>>19^s>>>10^s<<15^s<<13)+u[e&15]+u[(e+9)&15])|0
}f=(f+d+(m>>>6^m>>>11^m>>>25^m<<26^m<<21^m<<7)+(g^m&(l^g))+c[e]);d=g;g=l;l=m;m=n+f|0;n=o;o=p;p=r;r=(f+((p&o)^(n&(p^o)))+(p>>>2^p>>>13^p>>>22^p<<30^p<<19^p<<10))|0
}j[0]=j[0]+r|0;j[1]=j[1]+p|0;j[2]=j[2]+o|0;j[3]=j[3]+n|0;j[4]=j[4]+m|0;j[5]=j[5]+l|0;j[6]=j[6]+g|0;j[7]=j[7]+d|0}};sjcl.prng=function(a){this._pools=[new sjcl.hash.sha256()];
this._poolEntropy=[0];this._reseedCount=0;this._robins={};this._eventId=0;this._collectorIds={};this._collectorIdNext=0;this._strength=0;
this._poolStrength=0;this._nextReseed=0;this._key=[0,0,0,0,0,0,0,0];this._counter=[0,0,0,0];this._cipher=undefined;this._defaultParanoia=a;
this._collectorsStarted=false;this._callbacks={progress:{},seeded:{}};this._callbackI=0;this._NOT_READY=0;this._READY=1;this._REQUIRES_RESEED=2;
this._MAX_WORDS_PER_BURST=65536;this._PARANOIA_LEVELS=[0,48,64,96,128,192,256,384,512,768,1024];this._MILLISECONDS_PER_RESEED=30000;
this._BITS_PER_RESEED=80};sjcl.prng.prototype={randomWords:function(a,f){var b=[],d,c=this.isReady(f),e;if(c===this._NOT_READY){throw new sjcl.exception.notReady("generator isn't seeded")
}else{if(c&this._REQUIRES_RESEED){this._reseedFromPools(!(c&this._READY))}}for(d=0;d<a;d+=4){if((d+1)%this._MAX_WORDS_PER_BURST===0){this._gate()
}e=this._gen4words();b.push(e[0],e[1],e[2],e[3])}this._gate();return b.slice(0,a)},setDefaultParanoia:function(b,a){if(b===0&&a!=="Setting paranoia=0 will ruin your security; use it only for testing"){throw"Setting paranoia=0 will ruin your security; use it only for testing"
}this._defaultParanoia=b},addEntropy:function(e,l,a){a=a||"user";var b,f,g,j=(new Date()).valueOf(),c=this._robins[a],k=this.isReady(),d=0,h;
b=this._collectorIds[a];if(b===undefined){b=this._collectorIds[a]=this._collectorIdNext++}if(c===undefined){c=this._robins[a]=0
}if(isNaN(c)){c=this._robins[a]=0}this._robins[a]=(this._robins[a]+1)%this._pools.length;switch(typeof(e)){case"number":if(l===undefined){l=1
}this._pools[c].update([b,this._eventId++,1,l,j,1,e|0]);break;case"object":h=Object.prototype.toString.call(e);if(h==="[object Uint32Array]"){g=[];
for(f=0;f<e.length;f++){g.push(e[f])}e=g}else{if(h!=="[object Array]"){d=1}for(f=0;f<e.length&&!d;f++){if(typeof(e[f])!=="number"){d=1
}}}if(!d){if(l===undefined){l=0;for(f=0;f<e.length;f++){g=e[f];while(g>0){l++;g=g>>>1}}}this._pools[c].update([b,this._eventId++,2,l,j,e.length].concat(e))
}break;case"string":if(l===undefined){l=e.length}this._pools[c].update([b,this._eventId++,3,l,j,e.length]);this._pools[c].update(e);
break;default:d=1}if(d){throw new sjcl.exception.bug("random: addEntropy only supports number, array of numbers or string")
}this._poolEntropy[c]+=l;this._poolStrength+=l;if(k===this._NOT_READY){if(this.isReady()!==this._NOT_READY){this._fireEvent("seeded",Math.max(this._strength,this._poolStrength))
}this._fireEvent("progress",this.getProgress())}},isReady:function(b){var a=this._PARANOIA_LEVELS[(b!==undefined)?b:this._defaultParanoia];
if(this._strength&&this._strength>=a){return(this._poolEntropy[0]>this._BITS_PER_RESEED&&(new Date()).valueOf()>this._nextReseed)?this._REQUIRES_RESEED|this._READY:this._READY
}else{return(this._poolStrength>=a)?this._REQUIRES_RESEED|this._NOT_READY:this._NOT_READY}},getProgress:function(b){var a=this._PARANOIA_LEVELS[b?b:this._defaultParanoia];
if(this._strength>=a){return 1}else{return(this._poolStrength>a)?1:this._poolStrength/a}},startCollectors:function(){if(this._collectorsStarted){return
}this._eventListener={loadTimeCollector:this._bind(this._loadTimeCollector),mouseCollector:this._bind(this._mouseCollector),keyboardCollector:this._bind(this._keyboardCollector),accelerometerCollector:this._bind(this._accelerometerCollector),touchCollector:this._bind(this._touchCollector)};
if(window.addEventListener){window.addEventListener("load",this._eventListener.loadTimeCollector,false);window.addEventListener("mousemove",this._eventListener.mouseCollector,false);
window.addEventListener("keypress",this._eventListener.keyboardCollector,false);window.addEventListener("devicemotion",this._eventListener.accelerometerCollector,false);
window.addEventListener("touchmove",this._eventListener.touchCollector,false)}else{if(document.attachEvent){document.attachEvent("onload",this._eventListener.loadTimeCollector);
document.attachEvent("onmousemove",this._eventListener.mouseCollector);document.attachEvent("keypress",this._eventListener.keyboardCollector)
}else{throw new sjcl.exception.bug("can't attach event")}}this._collectorsStarted=true},stopCollectors:function(){if(!this._collectorsStarted){return
}if(window.removeEventListener){window.removeEventListener("load",this._eventListener.loadTimeCollector,false);window.removeEventListener("mousemove",this._eventListener.mouseCollector,false);
window.removeEventListener("keypress",this._eventListener.keyboardCollector,false);window.removeEventListener("devicemotion",this._eventListener.accelerometerCollector,false);
window.removeEventListener("touchmove",this._eventListener.touchCollector,false)}else{if(document.detachEvent){document.detachEvent("onload",this._eventListener.loadTimeCollector);
document.detachEvent("onmousemove",this._eventListener.mouseCollector);document.detachEvent("keypress",this._eventListener.keyboardCollector)
}}this._collectorsStarted=false},addEventListener:function(a,b){this._callbacks[a][this._callbackI++]=b},removeEventListener:function(e,a){var f,d,c=this._callbacks[e],b=[];
for(d in c){if(c.hasOwnProperty(d)&&c[d]===a){b.push(d)}}for(f=0;f<b.length;f++){d=b[f];delete c[d]}},_bind:function(b){var a=this;
return function(){b.apply(a,arguments)}},_gen4words:function(){for(var a=0;a<4;a++){this._counter[a]=this._counter[a]+1|0;
if(this._counter[a]){break}}return this._cipher.encrypt(this._counter)},_gate:function(){this._key=this._gen4words().concat(this._gen4words());
this._cipher=new sjcl.cipher.aes(this._key)},_reseed:function(b){this._key=sjcl.hash.sha256.hash(this._key.concat(b));this._cipher=new sjcl.cipher.aes(this._key);
for(var a=0;a<4;a++){this._counter[a]=this._counter[a]+1|0;if(this._counter[a]){break}}},_reseedFromPools:function(c){var a=[],d=0,b;
this._nextReseed=a[0]=(new Date()).valueOf()+this._MILLISECONDS_PER_RESEED;for(b=0;b<16;b++){a.push(Math.random()*4294967296|0)
}for(b=0;b<this._pools.length;b++){a=a.concat(this._pools[b].finalize());d+=this._poolEntropy[b];this._poolEntropy[b]=0;if(!c&&(this._reseedCount&(1<<b))){break
}}if(this._reseedCount>=1<<this._pools.length){this._pools.push(new sjcl.hash.sha256());this._poolEntropy.push(0)}this._poolStrength-=d;
if(d>this._strength){this._strength=d}this._reseedCount++;this._reseed(a)},_keyboardCollector:function(){this._addCurrentTimeToEntropy(1)
},_mouseCollector:function(c){var a,d;try{a=c.x||c.clientX||c.offsetX||0;d=c.y||c.clientY||c.offsetY||0}catch(b){a=0;d=0}if(a!=0&&d!=0){sjcl.random.addEntropy([a,d],2,"mouse")
}this._addCurrentTimeToEntropy(0)},_touchCollector:function(b){var d=b.touches[0]||b.changedTouches[0];var a=d.pageX||d.clientX,c=d.pageY||d.clientY;
sjcl.random.addEntropy([a,c],1,"touch");this._addCurrentTimeToEntropy(0)},_loadTimeCollector:function(){this._addCurrentTimeToEntropy(2)
},_addCurrentTimeToEntropy:function(a){if(typeof window!=="undefined"&&window.performance&&typeof window.performance.now==="function"){sjcl.random.addEntropy(window.performance.now(),a,"loadtime")
}else{sjcl.random.addEntropy((new Date()).valueOf(),a,"loadtime")}},_accelerometerCollector:function(b){var a=b.accelerationIncludingGravity.x||b.accelerationIncludingGravity.y||b.accelerationIncludingGravity.z;
if(window.orientation){var c=window.orientation;if(typeof c==="number"){sjcl.random.addEntropy(c,1,"accelerometer")}}if(a){sjcl.random.addEntropy(a,2,"accelerometer")
}this._addCurrentTimeToEntropy(0)},_fireEvent:function(d,a){var c,b=sjcl.random._callbacks[d],e=[];for(c in b){if(b.hasOwnProperty(c)){e.push(b[c])
}}for(c=0;c<e.length;c++){e[c](a)}}};sjcl.random=new sjcl.prng(6);(function(){try{var a,c,b;if(typeof window!=="undefined"&&typeof Uint32Array!=="undefined"){b=new Uint32Array(32);
if(window.crypto&&window.crypto.getRandomValues){window.crypto.getRandomValues(b)}else{if(window.msCrypto&&window.msCrypto.getRandomValues){window.msCrypto.getRandomValues(b)
}else{return}}sjcl.random.addEntropy(b,1024,"crypto.getRandomValues")}else{}}catch(d){if(typeof window!=="undefined"&&window.console){console.log("There was an error collecting entropy from the browser:");
console.log(d)}}}());sjcl.random=new sjcl.prng(0);sjcl.random.startCollectors();
/* All code below is covered by the following license.
 */
/*
 * Copyright (c) 2003-2005  Tom Wu
 * All Rights Reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS-IS" AND WITHOUT WARRANTY OF ANY KIND, 
 * EXPRESS, IMPLIED OR OTHERWISE, INCLUDING WITHOUT LIMITATION, ANY 
 * WARRANTY OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  
 *
 * IN NO EVENT SHALL TOM WU BE LIABLE FOR ANY SPECIAL, INCIDENTAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES OF ANY KIND, OR ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER OR NOT ADVISED OF
 * THE POSSIBILITY OF DAMAGE, AND ON ANY THEORY OF LIABILITY, ARISING OUT
 * OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 * In addition, the following condition applies:
 *
 * All redistributions must retain an intact copy of this copyright notice
 * and disclaimer.
 */
function parseBigInt(b,a){return new BigInteger(b,a)
}function linebrk(c,d){var a="";var b=0;while(b+d<c.length){a+=c.substring(b,b+d)+"\n";b+=d}return a+c.substring(b,c.length)
}function byte2Hex(a){if(a<16){return"0"+a.toString(16)}else{return a.toString(16)}}function pkcs1pad2(e,h){if(h<e.length+11){throw"Message too long for RSA"
}var g=new Array();var d=e.length-1;while(d>=0&&h>0){var f=e.charCodeAt(d--);if(f<128){g[--h]=f}else{if((f>127)&&(f<2048)){g[--h]=(f&63)|128;
g[--h]=(f>>6)|192}else{g[--h]=(f&63)|128;g[--h]=((f>>6)&63)|128;g[--h]=(f>>12)|224}}}g[--h]=0;var b=new SecureRandom();var a=new Array(h-2);
b.nextBytes(a);d=0;while(h>2){g[--h]=a[d];d++}g[--h]=2;g[--h]=0;return new BigInteger(g)}function RSAKey(){this.n=null;this.e=0;
this.d=null;this.p=null;this.q=null;this.dmp1=null;this.dmq1=null;this.coeff=null}function RSASetPublic(b,a){if(b!=null&&a!=null&&b.length>0&&a.length>0){this.n=parseBigInt(b,16);
this.e=parseInt(a,16)}else{throw"Error setting public key"}}function RSADoPublic(a){return a.modPowInt(this.e,this.n)}function RSAEncrypt(d){var a=pkcs1pad2(d,(this.n.bitLength()+7)>>3);
if(a==null){return null}var e=this.doPublic(a);if(e==null){return null}var b=e.toString(16);if((b.length&1)==0){return b}else{return"0"+b
}}RSAKey.prototype.doPublic=RSADoPublic;RSAKey.prototype.setPublic=RSASetPublic;RSAKey.prototype.encrypt=RSAEncrypt;var dbits;
var canary=244837814094590;var j_lm=((canary&16777215)==15715070);function BigInteger(e,d,f){if(e!=null){if("number"==typeof e){this.fromNumber(e,d,f)
}else{if(d==null&&"string"!=typeof e){this.fromString(e,256)}else{this.fromString(e,d)}}}}function nbi(){return new BigInteger(null)
}function am1(f,a,b,e,h,g){while(--g>=0){var d=a*this[f++]+b[e]+h;h=Math.floor(d/67108864);b[e++]=d&67108863}return h}function am2(f,q,r,e,o,a){var k=q&32767,p=q>>15;
while(--a>=0){var d=this[f]&32767;var g=this[f++]>>15;var b=p*d+g*k;d=k*d+((b&32767)<<15)+r[e]+(o&1073741823);o=(d>>>30)+(b>>>15)+p*g+(o>>>30);
r[e++]=d&1073741823}return o}function am3(f,q,r,e,o,a){var k=q&16383,p=q>>14;while(--a>=0){var d=this[f]&16383;var g=this[f++]>>14;
var b=p*d+g*k;d=k*d+((b&16383)<<14)+r[e]+o;o=(d>>28)+(b>>14)+p*g;r[e++]=d&268435455}return o}if(j_lm&&(navigator.appName=="Microsoft Internet Explorer")){BigInteger.prototype.am=am2;
dbits=30}else{if(j_lm&&(navigator.appName!="Netscape")){BigInteger.prototype.am=am1;dbits=26}else{BigInteger.prototype.am=am3;
dbits=28}}BigInteger.prototype.DB=dbits;BigInteger.prototype.DM=((1<<dbits)-1);BigInteger.prototype.DV=(1<<dbits);var BI_FP=52;
BigInteger.prototype.FV=Math.pow(2,BI_FP);BigInteger.prototype.F1=BI_FP-dbits;BigInteger.prototype.F2=2*dbits-BI_FP;var BI_RM="0123456789abcdefghijklmnopqrstuvwxyz";
var BI_RC=new Array();var rr,vv;rr="0".charCodeAt(0);for(vv=0;vv<=9;++vv){BI_RC[rr++]=vv}rr="a".charCodeAt(0);for(vv=10;vv<36;
++vv){BI_RC[rr++]=vv}rr="A".charCodeAt(0);for(vv=10;vv<36;++vv){BI_RC[rr++]=vv}function int2char(a){return BI_RM.charAt(a)
}function intAt(b,a){var d=BI_RC[b.charCodeAt(a)];return(d==null)?-1:d}function bnpCopyTo(b){for(var a=this.t-1;a>=0;--a){b[a]=this[a]
}b.t=this.t;b.s=this.s}function bnpFromInt(a){this.t=1;this.s=(a<0)?-1:0;if(a>0){this[0]=a}else{if(a<-1){this[0]=a+DV}else{this.t=0
}}}function nbv(a){var b=nbi();b.fromInt(a);return b}function bnpFromString(h,c){var e;if(c==16){e=4}else{if(c==8){e=3}else{if(c==256){e=8
}else{if(c==2){e=1}else{if(c==32){e=5}else{if(c==4){e=2}else{this.fromRadix(h,c);return}}}}}}this.t=0;this.s=0;var g=h.length,d=false,f=0;
while(--g>=0){var a=(e==8)?h[g]&255:intAt(h,g);if(a<0){if(h.charAt(g)=="-"){d=true}continue}d=false;if(f==0){this[this.t++]=a
}else{if(f+e>this.DB){this[this.t-1]|=(a&((1<<(this.DB-f))-1))<<f;this[this.t++]=(a>>(this.DB-f))}else{this[this.t-1]|=a<<f
}}f+=e;if(f>=this.DB){f-=this.DB}}if(e==8&&(h[0]&128)!=0){this.s=-1;if(f>0){this[this.t-1]|=((1<<(this.DB-f))-1)<<f}}this.clamp();
if(d){BigInteger.ZERO.subTo(this,this)}}function bnpClamp(){var a=this.s&this.DM;while(this.t>0&&this[this.t-1]==a){--this.t
}}function bnToString(c){if(this.s<0){return"-"+this.negate().toString(c)}var e;if(c==16){e=4}else{if(c==8){e=3}else{if(c==2){e=1
}else{if(c==32){e=5}else{if(c==4){e=2}else{return this.toRadix(c)}}}}}var g=(1<<e)-1,l,a=false,h="",f=this.t;var j=this.DB-(f*this.DB)%e;
if(f-->0){if(j<this.DB&&(l=this[f]>>j)>0){a=true;h=int2char(l)}while(f>=0){if(j<e){l=(this[f]&((1<<j)-1))<<(e-j);l|=this[--f]>>(j+=this.DB-e)
}else{l=(this[f]>>(j-=e))&g;if(j<=0){j+=this.DB;--f}}if(l>0){a=true}if(a){h+=int2char(l)}}}return a?h:"0"}function bnNegate(){var a=nbi();
BigInteger.ZERO.subTo(this,a);return a}function bnAbs(){return(this.s<0)?this.negate():this}function bnCompareTo(b){var d=this.s-b.s;
if(d!=0){return d}var c=this.t;d=c-b.t;if(d!=0){return d}while(--c>=0){if((d=this[c]-b[c])!=0){return d}}return 0}function nbits(a){var c=1,b;
if((b=a>>>16)!=0){a=b;c+=16}if((b=a>>8)!=0){a=b;c+=8}if((b=a>>4)!=0){a=b;c+=4}if((b=a>>2)!=0){a=b;c+=2}if((b=a>>1)!=0){a=b;
c+=1}return c}function bnBitLength(){if(this.t<=0){return 0}return this.DB*(this.t-1)+nbits(this[this.t-1]^(this.s&this.DM))
}function bnpDLShiftTo(c,b){var a;for(a=this.t-1;a>=0;--a){b[a+c]=this[a]}for(a=c-1;a>=0;--a){b[a]=0}b.t=this.t+c;b.s=this.s
}function bnpDRShiftTo(c,b){for(var a=c;a<this.t;++a){b[a-c]=this[a]}b.t=Math.max(this.t-c,0);b.s=this.s}function bnpLShiftTo(j,e){var b=j%this.DB;
var a=this.DB-b;var g=(1<<a)-1;var f=Math.floor(j/this.DB),h=(this.s<<b)&this.DM,d;for(d=this.t-1;d>=0;--d){e[d+f+1]=(this[d]>>a)|h;
h=(this[d]&g)<<b}for(d=f-1;d>=0;--d){e[d]=0}e[f]=h;e.t=this.t+f+1;e.s=this.s;e.clamp()}function bnpRShiftTo(g,d){d.s=this.s;
var e=Math.floor(g/this.DB);if(e>=this.t){d.t=0;return}var b=g%this.DB;var a=this.DB-b;var f=(1<<b)-1;d[0]=this[e]>>b;for(var c=e+1;
c<this.t;++c){d[c-e-1]|=(this[c]&f)<<a;d[c-e]=this[c]>>b}if(b>0){d[this.t-e-1]|=(this.s&f)<<a}d.t=this.t-e;d.clamp()}function bnpSubTo(d,f){var e=0,g=0,b=Math.min(d.t,this.t);
while(e<b){g+=this[e]-d[e];f[e++]=g&this.DM;g>>=this.DB}if(d.t<this.t){g-=d.s;while(e<this.t){g+=this[e];f[e++]=g&this.DM;
g>>=this.DB}g+=this.s}else{g+=this.s;while(e<d.t){g-=d[e];f[e++]=g&this.DM;g>>=this.DB}g-=d.s}f.s=(g<0)?-1:0;if(g<-1){f[e++]=this.DV+g
}else{if(g>0){f[e++]=g}}f.t=e;f.clamp()}function bnpMultiplyTo(c,e){var b=this.abs(),f=c.abs();var d=b.t;e.t=d+f.t;while(--d>=0){e[d]=0
}for(d=0;d<f.t;++d){e[d+b.t]=b.am(0,f[d],e,d,0,b.t)}e.s=0;e.clamp();if(this.s!=c.s){BigInteger.ZERO.subTo(e,e)}}function bnpSquareTo(d){var a=this.abs();
var b=d.t=2*a.t;while(--b>=0){d[b]=0}for(b=0;b<a.t-1;++b){var e=a.am(b,a[b],d,2*b,0,1);if((d[b+a.t]+=a.am(b+1,2*a[b],d,2*b+1,e,a.t-b-1))>=a.DV){d[b+a.t]-=a.DV;
d[b+a.t+1]=1}}if(d.t>0){d[d.t-1]+=a.am(b,a[b],d,2*b,0,1)}d.s=0;d.clamp()}function bnpDivRemTo(n,h,g){var x=n.abs();if(x.t<=0){return
}var k=this.abs();if(k.t<x.t){if(h!=null){h.fromInt(0)}if(g!=null){this.copyTo(g)}return}if(g==null){g=nbi()}var d=nbi(),a=this.s,l=n.s;
var w=this.DB-nbits(x[x.t-1]);if(w>0){x.lShiftTo(w,d);k.lShiftTo(w,g)}else{x.copyTo(d);k.copyTo(g)}var p=d.t;var b=d[p-1];
if(b==0){return}var o=b*(1<<this.F1)+((p>1)?d[p-2]>>this.F2:0);var B=this.FV/o,A=(1<<this.F1)/o,z=1<<this.F2;var u=g.t,s=u-p,f=(h==null)?nbi():h;
d.dlShiftTo(s,f);if(g.compareTo(f)>=0){g[g.t++]=1;g.subTo(f,g)}BigInteger.ONE.dlShiftTo(p,f);f.subTo(d,d);while(d.t<p){d[d.t++]=0
}while(--s>=0){var c=(g[--u]==b)?this.DM:Math.floor(g[u]*B+(g[u-1]+z)*A);if((g[u]+=d.am(0,c,g,s,0,p))<c){d.dlShiftTo(s,f);
g.subTo(f,g);while(g[u]<--c){g.subTo(f,g)}}}if(h!=null){g.drShiftTo(p,h);if(a!=l){BigInteger.ZERO.subTo(h,h)}}g.t=p;g.clamp();
if(w>0){g.rShiftTo(w,g)}if(a<0){BigInteger.ZERO.subTo(g,g)}}function bnMod(b){var c=nbi();this.abs().divRemTo(b,null,c);if(this.s<0&&c.compareTo(BigInteger.ZERO)>0){b.subTo(c,c)
}return c}function Classic(a){this.m=a}function cConvert(a){if(a.s<0||a.compareTo(this.m)>=0){return a.mod(this.m)}else{return a
}}function cRevert(a){return a}function cReduce(a){a.divRemTo(this.m,null,a)}function cMulTo(a,c,b){a.multiplyTo(c,b);this.reduce(b)
}function cSqrTo(a,b){a.squareTo(b);this.reduce(b)}Classic.prototype.convert=cConvert;Classic.prototype.revert=cRevert;Classic.prototype.reduce=cReduce;
Classic.prototype.mulTo=cMulTo;Classic.prototype.sqrTo=cSqrTo;function bnpInvDigit(){if(this.t<1){return 0}var a=this[0];
if((a&1)==0){return 0}var b=a&3;b=(b*(2-(a&15)*b))&15;b=(b*(2-(a&255)*b))&255;b=(b*(2-(((a&65535)*b)&65535)))&65535;b=(b*(2-a*b%this.DV))%this.DV;
return(b>0)?this.DV-b:-b}function Montgomery(a){this.m=a;this.mp=a.invDigit();this.mpl=this.mp&32767;this.mph=this.mp>>15;
this.um=(1<<(a.DB-15))-1;this.mt2=2*a.t}function montConvert(a){var b=nbi();a.abs().dlShiftTo(this.m.t,b);b.divRemTo(this.m,null,b);
if(a.s<0&&b.compareTo(BigInteger.ZERO)>0){this.m.subTo(b,b)}return b}function montRevert(a){var b=nbi();a.copyTo(b);this.reduce(b);
return b}function montReduce(a){while(a.t<=this.mt2){a[a.t++]=0}for(var c=0;c<this.m.t;++c){var b=a[c]&32767;var d=(b*this.mpl+(((b*this.mph+(a[c]>>15)*this.mpl)&this.um)<<15))&a.DM;
b=c+this.m.t;a[b]+=this.m.am(0,d,a,c,0,this.m.t);while(a[b]>=a.DV){a[b]-=a.DV;a[++b]++}}a.clamp();a.drShiftTo(this.m.t,a);
if(a.compareTo(this.m)>=0){a.subTo(this.m,a)}}function montSqrTo(a,b){a.squareTo(b);this.reduce(b)}function montMulTo(a,c,b){a.multiplyTo(c,b);
this.reduce(b)}Montgomery.prototype.convert=montConvert;Montgomery.prototype.revert=montRevert;Montgomery.prototype.reduce=montReduce;
Montgomery.prototype.mulTo=montMulTo;Montgomery.prototype.sqrTo=montSqrTo;function bnpIsEven(){return((this.t>0)?(this[0]&1):this.s)==0
}function bnpExp(h,j){if(h>4294967295||h<1){return BigInteger.ONE}var f=nbi(),a=nbi(),d=j.convert(this),c=nbits(h)-1;d.copyTo(f);
while(--c>=0){j.sqrTo(f,a);if((h&(1<<c))>0){j.mulTo(a,d,f)}else{var b=f;f=a;a=b}}return j.revert(f)}function bnModPowInt(b,a){var c;
if(b<256||a.isEven()){c=new Classic(a)}else{c=new Montgomery(a)}return this.exp(b,c)}BigInteger.prototype.copyTo=bnpCopyTo;
BigInteger.prototype.fromInt=bnpFromInt;BigInteger.prototype.fromString=bnpFromString;BigInteger.prototype.clamp=bnpClamp;
BigInteger.prototype.dlShiftTo=bnpDLShiftTo;BigInteger.prototype.drShiftTo=bnpDRShiftTo;BigInteger.prototype.lShiftTo=bnpLShiftTo;
BigInteger.prototype.rShiftTo=bnpRShiftTo;BigInteger.prototype.subTo=bnpSubTo;BigInteger.prototype.multiplyTo=bnpMultiplyTo;
BigInteger.prototype.squareTo=bnpSquareTo;BigInteger.prototype.divRemTo=bnpDivRemTo;BigInteger.prototype.invDigit=bnpInvDigit;
BigInteger.prototype.isEven=bnpIsEven;BigInteger.prototype.exp=bnpExp;BigInteger.prototype.toString=bnToString;BigInteger.prototype.negate=bnNegate;
BigInteger.prototype.abs=bnAbs;BigInteger.prototype.compareTo=bnCompareTo;BigInteger.prototype.bitLength=bnBitLength;BigInteger.prototype.mod=bnMod;
BigInteger.prototype.modPowInt=bnModPowInt;BigInteger.ZERO=nbv(0);BigInteger.ONE=nbv(1);function SecureRandom(){}SecureRandom.prototype.nextBytes=rng_get_bytes;
var b64map="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";var b64pad="=";function hex2b64(d){var b;var e;
var a="";for(b=0;b+3<=d.length;b+=3){e=parseInt(d.substring(b,b+3),16);a+=b64map.charAt(e>>6)+b64map.charAt(e&63)}if(b+1==d.length){e=parseInt(d.substring(b,b+1),16);
a+=b64map.charAt(e<<2)}else{if(b+2==d.length){e=parseInt(d.substring(b,b+2),16);a+=b64map.charAt(e>>2)+b64map.charAt((e&3)<<4)
}}while((a.length&3)>0){a+=b64pad}return a}function b64tohex(e){var c="";var d;var a=0;var b;for(d=0;d<e.length;++d){if(e.charAt(d)==b64pad){break
}v=b64map.indexOf(e.charAt(d));if(v<0){continue}if(a==0){c+=int2char(v>>2);b=v&3;a=1}else{if(a==1){c+=int2char((b<<2)|(v>>4));
b=v&15;a=2}else{if(a==2){c+=int2char(b);c+=int2char(v>>2);b=v&3;a=3}else{c+=int2char((b<<2)|(v>>4));c+=int2char(v&15);a=0
}}}}if(a==1){c+=int2char(b<<2)}return c}function b64toBA(e){var d=b64tohex(e);var c;var b=new Array();for(c=0;2*c<d.length;
++c){b[c]=parseInt(d.substring(2*c,2*c+2),16)}return b};