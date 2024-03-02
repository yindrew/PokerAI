from flask import Flask, request, jsonify
import encoder
from pokerGRU import PokerGRU
from stateManager import FinalState, GameState
import torch

app = Flask(__name__)



model_IP = PokerGRU(256, 3)
model_OOP = PokerGRU(256, 3)
optimizer_IP = torch.optim.Adam(model_IP.parameters(), lr=0.001)
optimizer_OOP = torch.optim.Adam(model_IP.parameters(), lr=0.001)


# when you recieve a game state, we will get the game state and convert it into a decision.
@app.route("/receive-game-state", methods=["POST"])
def receive_game_state():
    model_IP.eval()
    model_OOP.eval()
    game_state = request.json
    game_state = GameState.from_json(game_state)
    decision = make_decision(game_state)
    print(decision)
    return jsonify(decision)


# when you recieve a game state, we will get the game state and convert it into a decision.
@app.route("/receive-final-state", methods=["POST"])
def receive_final_state():
    final_state = request.json
    final_state = FinalState.from_json(final_state)
    model = PokerGRU(256, 3)
    # model.process_final_state(final_state)
    
    return "working"


# make a decision based on the current game state
def make_decision(game_state):
    
    input_tensor = game_state.convertToTensor(encoder)
    legalMoves = game_state.legalMoves
    # retrives the action from the nueral network based on the input tensor
    action, decisions, _ = PokerGRU(256, 3)(input_tensor, legalMoves)
    
    # return the action
    return encoder.decode_action(action)





if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=4999)
