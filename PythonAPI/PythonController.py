from flask import Flask, request, jsonify
import requests
import Encoder
from PokerGRU import PokerGRU

app = Flask(__name__)


# when you recieve a game state, we will get the game state and convert it into a decision.
@app.route("/receive-game-state", methods=["POST"])
def receive_game_state():
    game_state = request.json
    decision = make_decision(game_state)
    # send_decision(decision)
    return jsonify(decision)


# make a decision based on the current game state # to be done
def make_decision(game_state):
    input_tensor = Encoder.input_to_tensor(*parser(game_state["board"], game_state["hand"], game_state["log"]))

    action, action_probs = PokerGRU(256, 3)(input_tensor)

    actions = [
        "FOLD",
        "CHECK",
        "CALL",
        "BET SMALL",
        "BET MEDIUM",
        "BET BIG",
        "BET ALL IN",
        "RAISE SMALL",
        "RAISE MEDIUM",
        "RAISE BIG",
        "RAISE ALL IN",
        "ALL IN",
    ]
    print(action_probs)
    return actions[action]


# send the decision back to the java side for them to handle
def send_decision(decision):
    url = "http://localhost:8080/python-post"
    requests.post(url, json=decision)


def parser(board, hand, log):
    # Extract board cards
    board_cards = [
        card["value"] + card["suit"] for card in board["boardCards"][: board["size"]]
    ]

    # Extract hand cards
    hand_cards = [card["value"] + card["suit"] for card in hand["hand"]]

    # Extract log actions
    log_actions = [log_entry["action"] for log_entry in log["logs"][: log["size"]]]

    return board_cards, hand_cards, log_actions


if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=4999)
