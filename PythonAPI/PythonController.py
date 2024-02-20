from flask import Flask, request, jsonify
import requests
import Encoder

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
    
    
    print("-----------------------------------------------------------")
    ts = Encoder.input_to_tensor(handpy, boardpy, logpy, 15)
    print(ts)
    print(ts.size())


def handleLog(logpy):
    currentPotSize = .5
    preflop = True
    
    
    for i in logpy:
        temp = i[1]
        size = temp / currentPotSize
        if i[0] == "all in":
            size = "all in"
        elif (size == 0):
            size = "0"
        elif (size < .4):
            size = "small"
        elif (size < .8):
            size = "normal"
        else:
            size = "big"

        # everything that happens preflop will be medium size    
        if preflop:
            size = "normal"
        if i[0] == "CALL":
            preflop = False

        #update the size to be categorical
        i[1] = size
        currentPotSize += temp

    print(logpy)
        
    

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=4999)
