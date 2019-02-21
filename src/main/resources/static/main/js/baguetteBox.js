/*!
* baguetteBox.js
* @author feimosi
* @version 1.8.2
*/(function(root,factory){'use strict';if(typeof define==='function'&&define.amd){define(factory);}else if(typeof exports==='object'){module.exports=factory();}else{root.baguetteBox=factory();}}(this,function(){'use strict';var leftArrow='<svg width="44" height="60">'+
'<polyline points="30 10 10 30 30 50" stroke="rgba(255,255,255,0.5)" stroke-width="4"'+
'stroke-linecap="butt" fill="none" stroke-linejoin="round"/>'+
'</svg>',rightArrow='<svg width="44" height="60">'+
'<polyline points="14 10 34 30 14 50" stroke="rgba(255,255,255,0.5)" stroke-width="4"'+
'stroke-linecap="butt" fill="none" stroke-linejoin="round"/>'+
'</svg>',closeX='<svg width="30" height="30">'+
'<g stroke="rgb(160,160,160)" stroke-width="4">'+
'<line x1="5" y1="5" x2="25" y2="25"/>'+
'<line x1="5" y1="25" x2="25" y2="5"/>'+
'</g></svg>';var options={},defaults={captions:true,fullScreen:false,noScrollbars:false,titleTag:false,buttons:'auto',async:false,preload:2,animation:'slideIn',afterShow:null,afterHide:null,onChange:null,overlayBackgroundColor:'rgba(0,0,0,.8)'};var supports={};var overlay,slider,previousButton,nextButton,closeButton;var currentGallery=[];var currentIndex=0;var touch={};var touchFlag=false;var regex=/.+\.(gif|jpe?g|png|webp)/i;var data={};var imagesElements=[];var documentLastFocus=null;var overlayClickHandler=function(event){if(event.target.id.indexOf('baguette-img')!==-1){hideOverlay();}};var previousButtonClickHandler=function(event){event.stopPropagation?event.stopPropagation():event.cancelBubble=true;showPreviousImage();};var nextButtonClickHandler=function(event){event.stopPropagation?event.stopPropagation():event.cancelBubble=true;showNextImage();};var closeButtonClickHandler=function(event){event.stopPropagation?event.stopPropagation():event.cancelBubble=true;hideOverlay();};var touchstartHandler=function(event){touch.count++;if(touch.count>1){touch.multitouch=true;}
touch.startX=event.changedTouches[0].pageX;touch.startY=event.changedTouches[0].pageY;};var touchmoveHandler=function(event){if(touchFlag||touch.multitouch){return;}
event.preventDefault?event.preventDefault():event.returnValue=false;var touchEvent=event.touches[0]||event.changedTouches[0];if(touchEvent.pageX-touch.startX>40){touchFlag=true;showPreviousImage();}else if(touchEvent.pageX-touch.startX<-40){touchFlag=true;showNextImage();}else if(touch.startY-touchEvent.pageY>100){hideOverlay();}};var touchendHandler=function(){touch.count--;if(touch.count<=0){touch.multitouch=false;}
touchFlag=false;};var trapFocusInsideOverlay=function(event){if(overlay.style.display==='block'&&(overlay.contains&&!overlay.contains(event.target))){event.stopPropagation();initFocus();}};if(![].forEach){Array.prototype.forEach=function(callback,thisArg){for(var i=0;i<this.length;i++){callback.call(thisArg,this[i],i,this);}};}
if(![].filter){Array.prototype.filter=function(a,b,c,d,e){c=this;d=[];for(e=0;e<c.length;e++)
a.call(b,c[e],e,c)&&d.push(c[e]);return d;};}
function run(selector,userOptions){supports.transforms=testTransformsSupport();supports.svg=testSVGSupport();buildOverlay();removeFromCache(selector);bindImageClickListeners(selector,userOptions);}
function bindImageClickListeners(selector,userOptions){var galleryNodeList=document.querySelectorAll(selector);var selectorData={galleries:[],nodeList:galleryNodeList};data[selector]=selectorData;[].forEach.call(galleryNodeList,function(galleryElement){if(userOptions&&userOptions.filter){regex=userOptions.filter;}
var tagsNodeList=[];if(galleryElement.tagName==='A'){tagsNodeList=[galleryElement];}else{tagsNodeList=galleryElement.getElementsByTagName('a');}
tagsNodeList=[].filter.call(tagsNodeList,function(element){return regex.test(element.href);});if(tagsNodeList.length===0){return;}
var gallery=[];[].forEach.call(tagsNodeList,function(imageElement,imageIndex){var imageElementClickHandler=function(event){event.preventDefault?event.preventDefault():event.returnValue=false;prepareOverlay(gallery,userOptions);showOverlay(imageIndex);};var imageItem={eventHandler:imageElementClickHandler,imageElement:imageElement};bind(imageElement,'click',imageElementClickHandler);gallery.push(imageItem);});selectorData.galleries.push(gallery);});}
function clearCachedData(){for(var selector in data){if(data.hasOwnProperty(selector)){removeFromCache(selector);}}}
function removeFromCache(selector){if(!data.hasOwnProperty(selector)){return;}
var galleries=data[selector].galleries;[].forEach.call(galleries,function(gallery){[].forEach.call(gallery,function(imageItem){unbind(imageItem.imageElement,'click',imageItem.eventHandler);});if(currentGallery===gallery){currentGallery=[];}});delete data[selector];}
function buildOverlay(){overlay=getByID('baguetteBox-overlay');if(overlay){slider=getByID('baguetteBox-slider');previousButton=getByID('previous-button');nextButton=getByID('next-button');closeButton=getByID('close-button');return;}
overlay=create('div');overlay.setAttribute('role','dialog');overlay.id='baguetteBox-overlay';document.getElementsByTagName('body')[0].appendChild(overlay);slider=create('div');slider.id='baguetteBox-slider';overlay.appendChild(slider);previousButton=create('button');previousButton.setAttribute('type','button');previousButton.id='previous-button';previousButton.setAttribute('aria-label','Previous');previousButton.innerHTML=supports.svg?leftArrow:'<';overlay.appendChild(previousButton);nextButton=create('button');nextButton.setAttribute('type','button');nextButton.id='next-button';nextButton.setAttribute('aria-label','Next');nextButton.innerHTML=supports.svg?rightArrow:'>';overlay.appendChild(nextButton);closeButton=create('button');closeButton.setAttribute('type','button');closeButton.id='close-button';closeButton.setAttribute('aria-label','Close');closeButton.innerHTML=supports.svg?closeX:'×';overlay.appendChild(closeButton);previousButton.className=nextButton.className=closeButton.className='baguetteBox-button';bindEvents();}
function keyDownHandler(event){switch(event.keyCode){case 37:showPreviousImage();break;case 39:showNextImage();break;case 27:hideOverlay();break;}}
function bindEvents(){bind(overlay,'click',overlayClickHandler);bind(previousButton,'click',previousButtonClickHandler);bind(nextButton,'click',nextButtonClickHandler);bind(closeButton,'click',closeButtonClickHandler);bind(overlay,'touchstart',touchstartHandler);bind(overlay,'touchmove',touchmoveHandler);bind(overlay,'touchend',touchendHandler);bind(document,'focus',trapFocusInsideOverlay,true);}
function unbindEvents(){unbind(overlay,'click',overlayClickHandler);unbind(previousButton,'click',previousButtonClickHandler);unbind(nextButton,'click',nextButtonClickHandler);unbind(closeButton,'click',closeButtonClickHandler);unbind(overlay,'touchstart',touchstartHandler);unbind(overlay,'touchmove',touchmoveHandler);unbind(overlay,'touchend',touchendHandler);unbind(document,'focus',trapFocusInsideOverlay,true);}
function prepareOverlay(gallery,userOptions){if(currentGallery===gallery){return;}
currentGallery=gallery;setOptions(userOptions);while(slider.firstChild){slider.removeChild(slider.firstChild);}
imagesElements.length=0;var imagesFiguresIds=[];var imagesCaptionsIds=[];for(var i=0,fullImage;i<gallery.length;i++){fullImage=create('div');fullImage.className='full-image';fullImage.id='baguette-img-'+i;imagesElements.push(fullImage);imagesFiguresIds.push('baguetteBox-figure-'+i);imagesCaptionsIds.push('baguetteBox-figcaption-'+i);slider.appendChild(imagesElements[i]);}
overlay.setAttribute('aria-labelledby',imagesFiguresIds.join(' '));overlay.setAttribute('aria-describedby',imagesCaptionsIds.join(' '));}
function setOptions(newOptions){if(!newOptions){newOptions={};}
for(var item in defaults){options[item]=defaults[item];if(typeof newOptions[item]!=='undefined'){options[item]=newOptions[item];}}
slider.style.transition=slider.style.webkitTransition=(options.animation==='fadeIn'?'opacity .4s ease':options.animation==='slideIn'?'':'none');if(options.buttons==='auto'&&('ontouchstart'in window||currentGallery.length===1)){options.buttons=false;}
previousButton.style.display=nextButton.style.display=(options.buttons?'':'none');try{overlay.style.backgroundColor=options.overlayBackgroundColor;}catch(e){}}
function showOverlay(chosenImageIndex){if(options.noScrollbars){document.documentElement.style.overflowY='hidden';document.body.style.overflowY='scroll';}
if(overlay.style.display==='block'){return;}
bind(document,'keydown',keyDownHandler);currentIndex=chosenImageIndex;touch={count:0,startX:null,startY:null};loadImage(currentIndex,function(){preloadNext(currentIndex);preloadPrev(currentIndex);});updateOffset();overlay.style.display='block';if(options.fullScreen){enterFullScreen();}
setTimeout(function(){overlay.className='visible';if(options.afterShow){options.afterShow();}},50);if(options.onChange){options.onChange(currentIndex,imagesElements.length);}
documentLastFocus=document.activeElement;initFocus();}
function initFocus(){if(options.buttons){previousButton.focus();}else{closeButton.focus();}}
function enterFullScreen(){if(overlay.requestFullscreen){overlay.requestFullscreen();}else if(overlay.webkitRequestFullscreen){overlay.webkitRequestFullscreen();}else if(overlay.mozRequestFullScreen){overlay.mozRequestFullScreen();}}
function exitFullscreen(){if(document.exitFullscreen){document.exitFullscreen();}else if(document.mozCancelFullScreen){document.mozCancelFullScreen();}else if(document.webkitExitFullscreen){document.webkitExitFullscreen();}}
function hideOverlay(){if(options.noScrollbars){document.documentElement.style.overflowY='auto';document.body.style.overflowY='auto';}
if(overlay.style.display==='none'){return;}
unbind(document,'keydown',keyDownHandler);overlay.className='';setTimeout(function(){overlay.style.display='none';exitFullscreen();if(options.afterHide){options.afterHide();}},500);documentLastFocus.focus();}
function loadImage(index,callback){var imageContainer=imagesElements[index];var galleryItem=currentGallery[index];if(imageContainer===undefined||galleryItem===undefined){return;}
if(imageContainer.getElementsByTagName('img')[0]){if(callback){callback();}
return;}
var imageElement=galleryItem.imageElement;var thumbnailElement=imageElement.getElementsByTagName('img')[0];var imageCaption=typeof options.captions==='function'?options.captions.call(currentGallery,imageElement):imageElement.getAttribute('data-caption')||imageElement.title;var imageSrc=getImageSrc(imageElement);var figure=create('figure');figure.id='baguetteBox-figure-'+index;figure.innerHTML='<div class="baguetteBox-spinner">'+
'<div class="baguetteBox-double-bounce1"></div>'+
'<div class="baguetteBox-double-bounce2"></div>'+
'</div>';if(options.captions&&imageCaption){var figcaption=create('figcaption');figcaption.id='baguetteBox-figcaption-'+index;figcaption.innerHTML=imageCaption;figure.appendChild(figcaption);}
imageContainer.appendChild(figure);var image=create('img');image.onload=function(){var spinner=document.querySelector('#baguette-img-'+index+' .baguetteBox-spinner');figure.removeChild(spinner);if(!options.async&&callback){callback();}};image.setAttribute('src',imageSrc);image.alt=thumbnailElement?thumbnailElement.alt||'':'';if(options.titleTag&&imageCaption){image.title=imageCaption;}
figure.appendChild(image);if(options.async&&callback){callback();}}
function getImageSrc(image){var result=image.href;if(image.dataset){var srcs=[];for(var item in image.dataset){if(item.substring(0,3)==='at-'&&!isNaN(item.substring(3))){srcs[item.replace('at-','')]=image.dataset[item];}}
var keys=Object.keys(srcs).sort(function(a,b){return parseInt(a,10)<parseInt(b,10)?-1:1;});var width=window.innerWidth*window.devicePixelRatio;var i=0;while(i<keys.length-1&&keys[i]<width){i++;}
result=srcs[keys[i]]||result;}
return result;}
function showNextImage(){var returnValue;if(currentIndex<=imagesElements.length-2){currentIndex++;updateOffset();preloadNext(currentIndex);returnValue=true;}else if(options.animation){slider.className='bounce-from-right';setTimeout(function(){slider.className='';},400);returnValue=false;}
if(options.onChange){options.onChange(currentIndex,imagesElements.length);}
return returnValue;}
function showPreviousImage(){var returnValue;if(currentIndex>=1){currentIndex--;updateOffset();preloadPrev(currentIndex);returnValue=true;}else if(options.animation){slider.className='bounce-from-left';setTimeout(function(){slider.className='';},400);returnValue=false;}
if(options.onChange){options.onChange(currentIndex,imagesElements.length);}
return returnValue;}
function updateOffset(){var offset=-currentIndex*100+'%';if(options.animation==='fadeIn'){slider.style.opacity=0;setTimeout(function(){supports.transforms?slider.style.transform=slider.style.webkitTransform='translate3d('+offset+',0,0)':slider.style.left=offset;slider.style.opacity=1;},400);}else{supports.transforms?slider.style.transform=slider.style.webkitTransform='translate3d('+offset+',0,0)':slider.style.left=offset;}}
function testTransformsSupport(){var div=create('div');return typeof div.style.perspective!=='undefined'||typeof div.style.webkitPerspective!=='undefined';}
function testSVGSupport(){var div=create('div');div.innerHTML='<svg/>';return(div.firstChild&&div.firstChild.namespaceURI)==='http://www.w3.org/2000/svg';}
function preloadNext(index){if(index-currentIndex>=options.preload){return;}
loadImage(index+1,function(){preloadNext(index+1);});}
function preloadPrev(index){if(currentIndex-index>=options.preload){return;}
loadImage(index-1,function(){preloadPrev(index-1);});}
function bind(element,event,callback,useCapture){if(element.addEventListener){element.addEventListener(event,callback,useCapture);}else{element.attachEvent('on'+event,function(event){event=event||window.event;event.target=event.target||event.srcElement;callback(event);});}}
function unbind(element,event,callback,useCapture){if(element.removeEventListener){element.removeEventListener(event,callback,useCapture);}else{element.detachEvent('on'+event,callback);}}
function getByID(id){return document.getElementById(id);}
function create(element){return document.createElement(element);}
function destroyPlugin(){unbindEvents();clearCachedData();unbind(document,'keydown',keyDownHandler);document.getElementsByTagName('body')[0].removeChild(document.getElementById('baguetteBox-overlay'));data={};currentGallery=[];currentIndex=0;}
return{run:run,destroy:destroyPlugin,showNext:showNextImage,showPrevious:showPreviousImage};}));