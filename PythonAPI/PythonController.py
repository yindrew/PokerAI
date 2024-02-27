from flask import Flask, request, jsonify
import encoder
from pokerGRU import PokerGRU

app = Flask(__name__)


# when you recieve a game state, we will get the game state and convert it into a decision.
@app.route("/receive-game-state", methods=["POST"])
def receive_game_state():
    game_state = request.json
    
    # Validate game_state contents
    if not game_state or "hand" not in game_state or "board" not in game_state or "log" not in game_state:
        return jsonify({"error": "Invalid game state"}), 400

    if game_state["hand"] is None or game_state["board"] is None or game_state["log"] is None:
        return jsonify({"error": "Missing game state information"}), 400
    
    decision = make_decision(game_state)
    print(decision)
    return jsonify(decision)


# make a decision based on the current game state
def make_decision(game_state):
    
    # converts the game state to a input tensor
    input_tensor = encoder.input_to_tensor(game_state["hand"], game_state["board"], game_state["log"])


    # retrives the action from the nueral network based on the input tensor
    action, _ = PokerGRU(256, 3)(input_tensor)

    # return the action 
    return encoder.decode_action(action)



if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=4999)
