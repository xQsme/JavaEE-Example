/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.business.encoders;

import java.io.IOException;
import java.io.Writer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author jabroni
 */
public class JsonEncoder implements Encoder.TextStream<JsonObject>{

    @Override
    public void encode(JsonObject payload, Writer writer) throws EncodeException, IOException {
        JsonWriter jsonWriter = Json.createWriter(writer);
        jsonWriter.writeObject(payload);
    }

    @Override
    public void init(EndpointConfig config) {
        
    }

    @Override
    public void destroy() {
        
    }
    
}
