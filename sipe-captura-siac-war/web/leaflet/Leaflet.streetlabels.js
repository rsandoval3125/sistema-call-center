L.StreetLabels = L.LabelTextCollision.extend({
    options: {
        propertyName: "name",
        showLabelIf: null,
        fontStyle: {
            dynamicFontSize: false,
            fontSize: 10,
            fontSizeUnit: "px",
            lineWidth: 4,
            fillStyle: "black",
            strokeStyle: "white"
        }
    },
    initialize: function(options) {
        L.LabelTextCollision.prototype.initialize.call(this, options);
        L.Util.stamp(this);
        this._layers = this._layers || {}
    },
    _initContainer: function(options) {
        L.LabelTextCollision.prototype._initContainer.call(this, options);
        if (this._map) {
            var handleLayerChanges = function() {
                this._reset();
                this._redraw()
            }
            .bind(this);
            this._map.on("layerremove", L.Util.throttle(handleLayerChanges, 32, this))
        }
    },
    _text: function(ctx, layer) {
        if (layer && layer.feature && layer.feature.properties && layer.feature.properties[this.options.propertyName] !== "undefined") {
			if (this.options.showLabelIf) {
                if (this.options.showLabelIf.call(this, layer.feature) === false) {
                    return
                }
            }
            var layerText = layer.feature.properties[this.options.propertyName];
            ctx.globalAlpha = 1;
            var p;
            var textPoint;
			if(!layer._parts){
				//console.log("error a");
				//console.log(layer);
			}else{
				if (layer._parts.length === 0 || layer._parts[0].length === 0) {
					return
				}
			}
            if (layer instanceof L.Polygon && this._map.hasLayer(layer)) {
                p = this._getCentroid(layer)
            } else{
				if (layer instanceof L.Polyline && this._map.hasLayer(layer)){
					p = this._getCenter(layer._parts[0])
				}
				if (layer instanceof L.Marker && this._map.hasLayer(layer)){
					p = this._latlng
				}
				if (layer instanceof L.CircleMarker && this._map.hasLayer(layer)){
					p = layer._point
				}
			}
            if (!p) {
                return
            }
            var offsetX = 0;
            var offsetY = 0;
            ctx.lineWidth = this.options.fontStyle.lineWidth;
            var fontSize = this.options.fontStyle.fontSize;
            if (this._map && this.options.fontStyle.dynamicFontSize === true) {
                fontSize = this._getDynamicFontSize()
            }
            ctx.font = fontSize + this.options.fontStyle.fontSizeUnit + " 'Helvetica Neue',Helvetica,Arial,sans-serif";
            var textWidth = ctx.measureText(layerText).width + p.x;
            var textHeight = p.y + offsetY + 20;
            var bounds = L.bounds(L.point(p.x + offsetX, p.y + offsetY), L.point(textWidth, textHeight));
            if (this.options.collisionFlg) {
                for (var index in this._textList) {
                    var pointBounds = this._textList[index];
                    if (pointBounds.intersects(bounds)) {
                        return
                    }
                }
            }
            this._textList.push(bounds);
            ctx.fillStyle = this.options.fontStyle.fillStyle;
            ctx.strokeStyle = this.options.fontStyle.strokeStyle;
			if (layer instanceof L.Marker) {
                var textLength = ctx.measureText(layerText).width;
                ctx.strokeText(layerText, p.x + offsetX - textLength / 2, p.y + offsetY);
                ctx.fillText(layerText, p.x + offsetX - textLength / 2, p.y + offsetY)
            }
            if (layer instanceof L.Polygon || layer instanceof L.CircleMarker) {
                var textLength = ctx.measureText(layerText).width;
                ctx.strokeText(layerText, p.x + offsetX  / 2, p.y + offsetY);
                ctx.fillText(layerText, p.x + offsetX  / 2, p.y + offsetY)
				/*ctx.strokeText(layerText, p.x + offsetX - textLength / 2, p.y + offsetY);
                ctx.fillText(layerText, p.x + offsetX - textLength / 2, p.y + offsetY)*/
            }
            if (layer instanceof L.Polyline && !(layer instanceof L.Polygon) && !(layer instanceof L.CircleMarker)) {
				var layerJson=layer.toGeoJSON();
				var coord=cambiarOrdenCoordenada(layerJson.geometry.coordinates,[]);
				if(JSON.stringify(coord).indexOf("[[[")>=0){
					coord=coord[0];
				}
				var layer2=turf.lineString(coord);
				var ordenLayer=turf.booleanClockwise(layer2);
				layer
                if (layer._parts) {
                    ctx.textAlign = "center";
                    ctx.textBaseline = "middle";
                    ctx.lineWidth = 3;
                    layer._parts.forEach(function(part) {
                        var pathPoints = [];
						if(ordenLayer===true){
							for (var i = part.length - 1; i >= 0; i--) {
								var linePart = part[i];
								pathPoints.push(linePart.x);
								pathPoints.push(linePart.y)
							}
						}
						else{
							for (var i = 0; i < part.length; i++) {
								var linePart = part[i];
								pathPoints.push(linePart.x);
								pathPoints.push(linePart.y)
							}
						}
                        ctx.textPath(layerText, pathPoints)
                    })
                }
            }
			
        }
    },
    _getDynamicFontSize: function() {
        return parseInt(this._map.getZoom())
    },
    _getCentroid: function(layer) {
        if (layer && layer.getCenter && this._map) {
            var latlngCenter = layer.getCenter();
            var containerPoint = this._map.latLngToContainerPoint(latlngCenter);
            return this._map.containerPointToLayerPoint(containerPoint)
        }
    }
});
