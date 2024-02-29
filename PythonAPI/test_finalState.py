import unittest
from finalState import FinalGameState  # Replace 'your_module' with the name of the module where FinalGameState is defined

class test_finalState(unittest.TestCase):

    def setUp(self):
        self.mock_json_data = {
            'board': {
                'boardCards': [
                    {'suit': 'h', 'value': 'A', 'cardVal': 14},
                    {'suit': 'd', 'value': 'K', 'cardVal': 13},
                    {'suit': 'd', 'value': 'T', 'cardVal': 10}
                ],
                'size': 3
            },
            'hand': {
                'hand': [
                    {'suit': 'c', 'value': '2', 'cardVal': 2},
                    {'suit': 's', 'value': '3', 'cardVal': 3}
                ]
            },
            'gameLog': {
                'potSize': 100,
                'size': 2,
                'logs': [
                    {'action': 'CALL', 'size': 50},
                    {'action': 'RAISE SMALL', 'size': 50}
                ]
            },
            'amountWon': 200
        }

    def test_initialization_from_json(self):
        final_state = FinalGameState.from_json(self.mock_json_data)

        # Test the board
        self.assertEqual(len(final_state.board.boardCards), 3)
        self.assertEqual(final_state.board.boardCards[0].suit, 'h')
        self.assertEqual(final_state.board.boardCards[0].value, 'A')

        # Test the hand
        self.assertEqual(len(final_state.hand.hand), 2)
        self.assertEqual(final_state.hand.hand[0].suit, 'c')

        # Test the game log
        self.assertEqual(final_state.gameLog.potSize, 100)
        self.assertEqual(len(final_state.gameLog.logs), 2)
        self.assertEqual(final_state.gameLog.logs[0].action, 'CALL')

        # Test the amount won
        self.assertEqual(final_state.amountWon, 200)

    def test_convertToTensor(self):
        final_state = FinalGameState.from_json(self.mock_json_data)
        final_state.convertToTensor()

        


if __name__ == '__main__':
    unittest.main()
