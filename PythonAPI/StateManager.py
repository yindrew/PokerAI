import encoder
import torch  

class Card:
    def __init__(self, suit, value, cardVal):
        self.suit = suit
        self.value = value
        self.cardVal = cardVal

class Hand:
    def __init__(self, hand):
        self.hand = [Card(**card) for card in hand]

class Log:
    def __init__(self, action, size):
        self.action = action
        self.size = size

class GameLog:
    def __init__(self, potSize, size, logs):
        self.potSize = potSize
        self.size = size
        self.logs = [Log(**i) for i in logs]

class Board:
    def __init__(self, boardCards, size):
        self.boardCards = [Card(**card) for card in boardCards]
        self.size = size


class State: 
    def __init__(self, board, hand, gameLog, position):
        self.board = Board(**board)
        self.hand = Hand(**hand)
        self.gameLog = GameLog(**gameLog)   
        self.position = position     
    
    def convertToTensor(self, encoder):
        allCards = [card.value + card.suit for card in self.hand.hand] + [card.value + card.suit for card in self.board.boardCards] 
        allCards = torch.tensor([encoder.encode_card(card) for card in allCards] + [-1] * (7 - len(allCards)))
        
        log_embeddings = torch.cat([encoder.encode_action(act.action) for act in self.gameLog.logs] + [torch.zeros(11) for _ in range(15 - self.gameLog.size)]).flatten()
        outputTensor = torch.cat((allCards, log_embeddings), dim=0)
        
        return outputTensor
    
    @classmethod
    def from_json(cls, json_data):
        return cls(**json_data)


class GameState(State):
    def __init__(self, board, hand, gameLog, legalMoves, position):
        super().__init__(board, hand, gameLog, position)
        self.legalMoves = legalMoves
        
        
class FinalState(State):
    def __init__(self, board, hand, gameLog, amountWon, position):
        super().__init__(board, hand, gameLog, position)
        self.amountWon = amountWon
        
