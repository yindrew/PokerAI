from flask import Flask, request, jsonify
import encoder
from pokerGRU import PokerGRU
from stateManager import FinalState, GameState
import torch

app = Flask(__name__)




model_IP = PokerGRU(256, 3)
model_IP.load_state_dict(torch.load('IP_state_dict.pth'))
model_OOP = PokerGRU(256, 3)
model_IP.load_state_dict(torch.load('OOP_state_dict.pth'))

optimizer_IP = torch.optim.Adam(model_IP.parameters(), lr=0.001)
optimizer_OOP = torch.optim.Adam(model_IP.parameters(), lr=0.001)


# when completed training, save the state dictionary
@app.route("/receive-training-complete", methods=["POST"])
def receive_training_complete():
    torch.save(model_IP.state_dict(), 'IP_state_dict.pth')
    torch.save(model_OOP.state_dict(), 'OOP_state_dict.pth')
    
    return "Training complete and models saved successfully", 200




# when you recieve a game state, we will get the game state and convert it into a decision.
@app.route("/receive-game-state", methods=["POST"])
def receive_game_state():
    model_IP.train()
    model_OOP.train()
    game_state = request.json
    game_state = GameState.from_json(game_state)
    currentModel = model_IP if game_state.position == 0 else model_OOP
    decision = make_decision(game_state, currentModel)
    print(decision)
    return jsonify(decision)


# when you recieve a game state, we will get the game state and convert it into a decision.
@app.route("/receive-final-state", methods=["POST"])
def receive_final_state():
    final_state = request.json
    final_state = FinalState.from_json(final_state)
    currentModel, currentOptimizer = (model_IP, optimizer_IP) if final_state.position == 0 else (model_OOP, optimizer_OOP)
    currentModel.process_final_state(final_state, currentOptimizer)
    
    return "working"

 
# make a decision based on the current game state
def make_decision(game_state, currentModel):
    
    input_tensor = game_state.convertToTensor(encoder)
    legalMoves = game_state.legalMoves
    # retrives the action from the nueral network based on the input tensor
    action, decisions, _ = currentModel(input_tensor, legalMoves)
    
    # return the action
    return encoder.decode_action(action)





if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=4999)
