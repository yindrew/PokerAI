import json

class Card:
    def __init__(self, suit, value, cardVal):
        self.suit = suit
        self.value = value
        self.cardVal = cardVal

class Hand:
    def __init__(self, hand):
        self.hand = [Card(**card) for card in hand]

class GameLogEntry:
    def __init__(self, action, size):
        self.action = action
        self.size = size

class GameLog:
    def __init__(self, potSize, size, logs):
        self.potSize = potSize
        self.size = size
        self.logs = [GameLogEntry(**log) for log in logs]

class Board:
    def __init__(self, boardCards, size):
        self.boardCards = [Card(**card) for card in boardCards]
        self.size = size

class FinalGameState:
    def __init__(self, board, hand, gameLog, amountWon):
        self.board = Board(**board)
        self.hand = Hand(**hand)
        self.gameLog = GameLog(**gameLog)
        self.amountWon = amountWon
        
    def convertToTensor():
        return 0

    @classmethod
    def from_json(cls, json_data):
        return cls(**json_data)
