"use strict";

window.Map = (typeof Map !== "undefined") ? Map : function Map() {

    this.dict = {};

    this.size = function() {
        return Object.keys(this.dict).length;
    };

    this.get = function(key){
        return this.dict[key];
    };

    this.has = function(key){
        return this.get(key) === undefined ? false : true;
    };

    this.put = function(key,value) {
        this.dict[key] = value;
    };

    this.delete = function(key) {
        delete this.dict[key];
    };

    this.clear = function(){
        this.dict = {};
    };

    this.forEach = function(callback) {
        var len = this.size();
        for (i = 0; i < len; i++) {
            var item = this.get( Object.keys(this.dict)[i] );
            callback(item);
        }
    }
}