from flask import Flask, request, jsonify
import encoder
from pokerGRU import PokerGRU
from StateManager import FinalGameState

app = Flask(__name__)


# when you recieve a game state, we will get the game state and convert it into a decision.
@app.route("/receive-game-state", methods=["POST"])
def receive_game_state():
    game_state = request.json
    print(game_state)
    decision = make_decision(game_state)
    print(decision)
    return jsonify(decision)


# when you recieve a game state, we will get the game state and convert it into a decision.
@app.route("/receive-final-state", methods=["POST"])
def receive_final_state():
    final_state = request.json
    finalState = FinalGameState.from_json(final_state)
    print(finalState.gameLog.potSize)    
    print(finalState.board.size)
    return jsonify(final_state)


# make a decision based on the current game state
def make_decision(game_state):
    
    # converts the game state to a input tensor
    input_tensor = encoder.input_to_tensor(game_state["hand"], game_state["board"], game_state["gameLog"])
    legal_moves = game_state["legalMoves"]
    # retrives the action from the nueral network based on the input tensor
    action, decisions, _ = PokerGRU(256, 3)(input_tensor, legal_moves)
    
    # return the action 
    return encoder.decode_action(action)



if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=4999)
