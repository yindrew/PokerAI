from flask import Flask, request, jsonify
import requests
import Testing

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
    
    parser(board, hand, log)
    
    return board['boardCards'][0]
    # Placeholder for AI decision logic
    return game_state

# send the decision back to the java side for them to handle
def send_decision(decision):
    url = 'http://localhost:8080/python-post'
    requests.post(url, json=decision)


def parser(board, hand, log):
    print("-----------------------------------------------------------")
    boardArr = board['boardCards']
    boardpy = []
    handpy = []
    logpy = []

    # convet the board
    for i in range(board['size']):
        boardpy.append(boardArr[i]['value'] + boardArr[i]['suit'])

    # convert the hand
    handpy.append(hand['hand'][0]['value'] + hand['hand'][0]['suit'])
    handpy.append(hand['hand'][1]['value'] + hand['hand'][1]['suit'])

    # convert the log
    for i in range(log['size']):
        logpy.append([log['logs'][i]['action'], log['logs'][i]['size']])

    print(handpy)
    print(boardpy)
    handleLog(logpy)
    
    print(Testing.card_to_index(handpy[0]), Testing.card_to_index(handpy[1]))
    print("-----------------------------------------------------------")


def handleLog(logpy):
    currentPotSize = 0
    preflop = True
    
    
    for i in logpy:
        currentPotSize += i[1]
        size = i[1] / currentPotSize
        if (size == 0):
            size = "0"
        elif (size < .4):
            size = "small"
        elif (size < .8):
            size = "normal"
        elif (size < 2):
            size = "big"
        else:
            size = "all in"
            
        if preflop:
            size = "normal"
        if i[0] == "CALL":
            preflop = False
        i[1] = size
    print(logpy)
        
    

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=4999)
