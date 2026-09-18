package com.gameexpert.ws.handler;

import com.gameexpert.engine.PlayerAction;
import com.gameexpert.engine.WorldEngineManager;
import com.gameexpert.ws.WsMessageContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

@Component
@RequiredArgsConstructor
public class MoveWsHandler implements WsMessageHandler {
    private final WorldEngineManager engineManager;

    @Override
    public String type() {
        return "move";
    }

    @Override
    public void handle(WsMessageContext context, JsonNode message) {
        String finalSceneActionId = WsFields.optionalFinalSceneActionId(message);
        double x = WsFields.finiteNumber(message, "x");
        double y = WsFields.finiteNumber(message, "y");
        double z = WsFields.finiteNumber(message, "z");
        float yaw = WsFields.finiteFloat(message, "yaw");
        float pitch = WsFields.finiteFloat(message, "pitch");
        boolean crouching = WsFields.booleanValue(message, "crouching");
        boolean gliding = WsFields.booleanValue(message, "gliding");

        PlayerAction.Move move = new PlayerAction.Move(context.nickname(), x, y, z, yaw, pitch, crouching, gliding, finalSceneActionId);
        engineManager.enqueue(context.worldId(), move);
    }
}
