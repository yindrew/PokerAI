import unittest
from stateManager import FinalState, GameState 

class test_finalState(unittest.TestCase):

    def setUp(self):                
                
        self.mock_final_state = {
        'board': 
            {'boardCards': [
                {'suit': 'h', 'value': '9', 'cardVal': 9}, 
                {'suit': 'd', 'value': 'T', 'cardVal': 10}, 
                {'suit': 's', 'value': 'K', 'cardVal': 13}
                ], 
                'size': 3}, 
            'hand': {
                'hand': [
                    {'suit': 'd', 'value': 'A', 'cardVal': 14}, 
                    {'suit': 's', 'value': 'A', 'cardVal': 14}]}, 
            'gameLog': {
                'potSize': 128.0, 
                'size': 6, 
                'logs': [
                    {'action': 'BET_MEDIUM', 'size': 3.0}, 
                    {'action': 'BET_MEDIUM', 'size': 10.0}, 
                    {'action': 'CALL', 'size': 10.0}, 
                    {'action': 'CHECK', 'size': 0.0}, 
                    {'action': 'BET_SMALL', 'size': 15.0}, 
                    {'action': 'BET_ALL_IN', 'size': 90.0}
                ]
            },
            'amountWon': 200
        }
        
        self.mock_game_state = {
            'board': 
                {'boardCards': [
                    {'suit': 'h', 'value': '9', 'cardVal': 9}, 
                    {'suit': 'd', 'value': 'T', 'cardVal': 10}, 
                    {'suit': 's', 'value': 'K', 'cardVal': 13}
                    ], 
                    'size': 3}, 
                'hand': {
                    'hand': [
                        {'suit': 'd', 'value': 'A', 'cardVal': 14}, 
                        {'suit': 's', 'value': 'A', 'cardVal': 14}]}, 
                'gameLog': {
                    'potSize': 128.0, 
                    'size': 6, 
                    'logs': [
                        {'action': 'BET_MEDIUM', 'size': 3.0}, 
                        {'action': 'BET_MEDIUM', 'size': 10.0}, 
                        {'action': 'CALL', 'size': 10.0}, 
                        {'action': 'CHECK', 'size': 0.0}, 
                        {'action': 'BET_SMALL', 'size': 15.0}, 
                        {'action': 'BET_ALL_IN', 'size': 90.0}
                    ]
                }, 
                'legalMoves': [0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0]
                }

    def test_FinalGameState(self):
        final_state = FinalState.from_json(self.mock_final_state)

        # Test the board
        self.assertEqual(len(final_state.board.boardCards), 3)
        self.assertEqual(final_state.board.boardCards[0].suit, 'h')
        self.assertEqual(final_state.board.boardCards[0].value, '9')

        # Test the hand
        self.assertEqual(len(final_state.hand.hand), 2)
        self.assertEqual(final_state.hand.hand[0].suit, 'd')

        # Test the game log
        self.assertEqual(final_state.gameLog.potSize, 128)
        self.assertEqual(len(final_state.gameLog.logs), 6)
        self.assertEqual(final_state.gameLog.logs[0].action, 'BET_MEDIUM')

        # Test the amount won
        self.assertEqual(final_state.amountWon, 200)
        
        
    def test_GameState(self):
        game_state = GameState.from_json(self.mock_game_state)

        # Test the board
        self.assertEqual(len(game_state.board.boardCards), 3)
        self.assertEqual(game_state.board.boardCards[0].suit, 'h')
        self.assertEqual(game_state.board.boardCards[0].value, '9')

        # Test the hand
        self.assertEqual(len(game_state.hand.hand), 2)
        self.assertEqual(game_state.hand.hand[0].suit, 'd')

        # Test the game log
        self.assertEqual(game_state.gameLog.potSize, 128)
        self.assertEqual(len(game_state.gameLog.logs), 6)
        self.assertEqual(game_state.gameLog.logs[0].action, 'BET_MEDIUM')

        # Test the legal moves
        self.assertEqual(game_state.legalMoves[0], 0)

    def test_convertToTensor(self):
        final_state = FinalState.from_json(self.mock_final_state)
        final_state.convertToTensor()



        


if __name__ == '__main__':
    unittest.main()
