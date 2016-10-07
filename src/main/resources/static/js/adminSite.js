(function() {
    _.templateSettings = {
            interpolate : /\{%\=(.+?)%\}/g,
            evaluate : /\{%(.+?)%\}/g
    };
	
    var _sync = Backbone.sync;
    Backbone.sync = function(method, model, options) {
    	options.beforeSend = function(xhr) {
			xhr.setRequestHeader("token", ___token___);
		};
		_sync(method, model, options);
    };
    
	var Site = Backbone.Model.extend({
		initialize: function() {
		},
		defaults: {
			pattern: "",
			name: "",
			risk: "",
			type: ""
		},
		isNew: function() {
			var isNullId = typeof(this.attributes.id) === 'undefined';
			if (isNullId == false) {
				this.url += "/" + this.attributes.id;
			}
			return isNullId;
		},
//		idAttribute: "id",
		url:"/sites",
		defauls: {
			pattern:"",name:"",risk:"",type:1
		}
	});
	
	SiteList = Backbone.Collection.extend({
		model: Site,
		url: "/sites",
		beforeSend: function(xhr) {
			xhr.setRequestHeader("token", "token");
			xhr.setRequestHeader("pass", "abcd");
		}
	});
	
	var showElement = function(ele) {
		$(ele).css('display','');
	}
	
	var hideElement = function(ele) {
		$(ele).css('display','none');
	}
	
	var isHidden = function(ele) {
		return $(ele).css("display")=='none';
	}

	var templateSiteRow = _.template($('#templateSiteRow').html());
	var templateNewSiteRow = _.template($('#templateNewSiteRow').html());
	
	var showElement = function(ele) {
		$(ele).css('display','');
	}
	
	var hideElement = function(ele) {
		$(ele).css('display','none');
	}
	
	var isHidden = function(ele) {
		return $(ele).css("display")=='none';
	}
	
	SiteView = Backbone.View.extend({
		el:$("#siteView"),
		_editmode: false,
		render: function() {
			var sites = this.collection.models;
			for (var i= 0; i< sites.length; i++) {
            	this.addSiteRow(sites[i].attributes);
			}
		},
		events: {
			"click #addSiteIcon" : "openNewSiteRow",
			"click #submitNewSiteButton" : "submitNewSite"
		},
		initialize: function() {
            this.siteTab = $("#siteTab");
            this.siteTabBody = $("#siteTabBody");
            this.startListenCollection();
		},
        startListenCollection: function() {
        	var that = this;
            this.listenTo(this.collection, "add", function(event) {
                var siteModel = event;
                var site = siteModel.attributes;
                var lastTrNum = $("#siteTabBody").children("tr").length - 1;
            	var lastSiteUrl = $("#newSiteUrl_" + lastTrNum).val();
            	if (lastSiteUrl === site.pattern) {
                	$("#site_tr_" + lastTrNum).remove();
            	}
                that.addSiteRow(site);
            });
            this.listenTo(this.collection, "change", function(event) {
            	that.clear();
            	that.render();
            });
            this.listenTo(this.collection, "reset", function(event) {
            	that.clear();
            	that.render();
            });
        },
        
        stopListenCollection: function() {
        	this.stopListening(this.collection, "add");
        },
		openNewSiteRow: function() {
        	if (this._editmode === true) {
        		alert("有其它网站名单正在编辑中，请先完成再操作");
           		return;
        	}
        	this._editmode = true;
            var rowNum = $("#siteTabBody").children("tr").length;
            var tbodyEnd = rowNum
            var color = rowNum % 2==0 ? "#dff0d8" : "#fcf8e3";
			var site = { "pattern":"", "name":"", "risk":"", "type":"" };
            var newSiteRow = templateNewSiteRow({trId:rowNum, site:site, color:color});
            this.siteTabBody.append(newSiteRow);
            var that = this;
            $("#newSiteEdit_" + rowNum).click(function() {
                var site = { "pattern":$.trim($("#newSiteUrl_" + rowNum).val()), 
                		     "name":$.trim($("#newSiteName_" + rowNum).val()),
                		     "risk":$.trim($("#newSiteRisk_" + rowNum).val()),
                		     "type":$.trim($("#newSiteType_" + rowNum).val())
                };
                that.collection.create(site, {
                	success: function(model, response) {
//                        $("#site_tr_" + rowNum).remove();
//                        that.addSiteRow(site);
                		that._editmode = false;
                	},
                	error: function(error) {
	           		 	alert("创建失败:" + site.pattern);
	           		    console.log("创建失败:" + error);
                	},
                	wait: true
                });
            });            
            $("#newSiteDelete_" + rowNum).click(function() {
                $("#site_tr_" + rowNum).remove();
            	that._editmode = false;
            });
		},
		
		clear: function() {
        	var that = this;
            var rowNum = $("#siteTabBody").children("tr").length;
            $("#siteViewBody").css('visibility',false);
        	for (var i=0; i<rowNum; i++) {
        		 $("#site_tr_" + i).remove();
            }            
        	$("#siteViewBody").css('visibility',true);
		},
		refresh: function() {
			this.clear();
			this.collection.fetch();
		},
        show: function() {
        	$("#siteView").css('display','block');
        },
        
        hide: function() {
        	$("#siteView").css('display','none');
        },
        addSiteRow : function(site) {
        	var that = this;
            var rowNum = $("#siteTabBody").children("tr").length;
            var tbodyEnd = rowNum
            var color = rowNum % 2==0 ? "#dff0d8" : "#fcf8e3";
            var siteRow = templateSiteRow({trId:rowNum, site:site, color:color});
            this.siteTabBody.append(siteRow);
            $("#siteEdit_" + rowNum).click(function() {
            	if (that._editmode === true) {
            		alert("有其它网站名单正在编辑中，请先完成再操作");
            		return;
            	}
            	that._editmode = true;
            	var site = { 
            		"pattern":$("#siteUrl_div_" + rowNum).text(), 
              		"name":$("#siteName_div_" + rowNum).text(),
              		"risk":$("#siteRisk_div_" + rowNum).text(),
              		"type":$("#siteType_div_" + rowNum).text()
            	};
            	$("#inputName_" + rowNum).val(site.name);
            	$("#inputType_" + rowNum).val(site.type);
            	$("#inputRisk_" + rowNum).val(site.risk);
            	hideElement("#siteName_div_" + rowNum);
            	showElement("#inputName_" + rowNum);
            	hideElement("#siteType_div_" + rowNum);
            	showElement("#inputType_" + rowNum);
            	hideElement("#siteRisk_div_" + rowNum);
            	showElement("#inputRisk_" + rowNum);
            	hideElement("#siteEdit_" + rowNum);
            	showElement("#siteEditDone_" + rowNum);
            });
            $("#siteEditDone_" + rowNum).click(function() {
        		var siteList = that.collection.models;
        		var site = siteList[rowNum];
            	var newSite = new Site({
            		"id":$("#siteId_" + rowNum).text(), 
            		"pattern":$.trim($("#siteUrl_div_" + rowNum).text()), 
              		"name":$.trim($("#siteName_div_" + rowNum).text()),
          		    "risk":$.trim($("#inputRisk_" + rowNum).val()),
          		    "type":$.trim($("#inputType_" + rowNum).val())
            	});
            	newSite.save(null, {
	            	success: function(model, response) {
	            		site.set(newSite.attributes);
	                	$("#siteRisk_div_" + rowNum).val(site.risk);
	                	$("#siteType_div_" + rowNum).val(site.type);
	                	hideElement("#inputName_" + rowNum);
	                	showElement("#siteName_div_" + rowNum);
	                	hideElement("#inputRisk_" + rowNum);
	                	showElement("#siteRisk_div_" + rowNum);
	                	hideElement("#inputType_" + rowNum);
	                	showElement("#siteType_div_" + rowNum);
	                	hideElement("#siteEditDone_" + rowNum);
	                	showElement("#siteEdit_" + rowNum);
	                	that._editmode = false;
	            	},
	            	error: function(error) {
	           		 	alert("修改失败:" + site.pattern);
	           		    console.log("修改失败:" + error);
	            	},
	            	wait:true
	            });
            });
            $("#siteDelete_" + rowNum).click(function() {
            	if (isHidden("#siteEdit_" + rowNum)) { //current is update mode, delete icon means "cancel"
                	hideElement("#inputName_" + rowNum);
                	showElement("#siteName_div_" + rowNum);
                	hideElement("#inputRisk_" + rowNum);
                	showElement("#siteRisk_div_" + rowNum);
                	hideElement("#inputType_" + rowNum);
                	showElement("#siteType_div_" + rowNum);
                	hideElement("#siteEditDone_" + rowNum);
                	showElement("#siteEdit_" + rowNum);
                	that._editmode = false;
            	} else if (that._editmode === true) {
            		alert("有其它网站名单正在编辑中，请先完成再操作");
               		return;
               	} else {
//            		if(confirm("你确定要将该网站移除黑名单吗？")) { //current is view mode, delete icon means "remove"
            		var siteList = that.collection.models;
            		var site = siteList[rowNum];
                   	site.destroy({
                   		success: function(model, response) {
                   			that.collection.remove(site);
                   			that.clear();
                   			that.render();
                   			that._editmode = false;
                   		},
                   		error: function(error){
	            		 	 alert("删除失败:" + site.pattern);
	               		 	 console.log("删除失败:" + error);
                   		},
                   		wait:true
                   	 });
//            		}
            	}
            });            
        }
	});
	
	$(document).ready(function(){
		var siteList = new SiteList(Site);
		___siteView___ = new SiteView({collection: siteList});
//		___siteView___.refresh();
//		___siteView___.show();
	});
})();