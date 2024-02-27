from flask import Flask, request, jsonify
import encoder
from pokerGRU import PokerGRU

app = Flask(__name__)


# when you recieve a game state, we will get the game state and convert it into a decision.
@app.route("/receive-game-state", methods=["POST"])
def receive_game_state():
    game_state = request.json
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
