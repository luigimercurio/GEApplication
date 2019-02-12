if (! Set) function Set() {
	this._values = {};
	this._size = 0;

	this.add = function (value) {
		if (!this.contains(value)) {
			this._values[this._hashFunction(value)] = value;
			this._size++;
		}
	},

	this.remove = function (value) {
		if (this.contains(value)) {
			delete this._values[this._hashFunction(value)];
			this._size--;
		}
	},

	this.has = function (value) {
		return typeof this._values[this._hashFunction(value)] !== "undefined";
	},

	this.size = function () {
		return this._size;
	},

	this.forEach = function forEach(iteratorFunction, thisObj) {
		for (var value in this._values) {
			iteratorFunction.call(thisObj, this._values[value]);
		}
	}
};