from flask import Flask, request, jsonify
import requests

app = Flask(__name__)


# when you recieve a game state, we will get the game state and convert it into a decision.
@app.route('/receive-game-state', methods=['POST'])
def receive_game_state():
    game_state = request.json
    
    print(game_state)
    decision = make_decision(game_state)
    #send_decision(decision)
    return jsonify(decision)


# make a decision based on the current game state
def make_decision(game_state):
    board = game_state['board']
    hand = game_state['hand']
    log = game_state['log']
    
    print("This is the board: ")
    print(board['boardCards'][0]['suit'])
    print("This is the hand ")
    print(hand)
    print("This is the log: ")
    print(log)
    
    
    return board['boardCards'][0]
    # Placeholder for AI decision logic
    return game_state

# send the decision back to the java side for them to handle
def send_decision(decision):
    url = 'http://localhost:8080/python-post'
    requests.post(url, json=decision)


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=4999)
