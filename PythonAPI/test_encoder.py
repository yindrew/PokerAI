import unittest
import torch
from encoder import encode_card, encode_action, input_to_tensor  # Adjust the import statement based on your project structure

class test_Encoder(unittest.TestCase):

    def test_encode_card(self):
        # Test the encode_card function
        # Example: Assert that the encoding for the card '2h' is correct
        self.assertEqual(encode_card('2s'), 0)
        self.assertEqual(encode_card('Ah'), 51)

    def test_encode_action(self):
        # Test the encode_action function
        # Example: Check if the tensor for 'FOLD' is correctly one-hot encoded
        expected_fold_encoding = torch.tensor([1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
        self.assertTrue(torch.equal(encode_action('FOLD'), expected_fold_encoding))

    def test_input_to_tensor(self):
        # Test the input_to_tensor function
        # Provide a mock hand, board, and game log and test the output
        hand = {'hand': [{'suit': 'd', 'value': 'A', 'cardVal': 14}, {'suit': 's', 'value': 'A', 'cardVal': 14}]}
        board = {'boardCards': [{'suit': 'h', 'value': '9', 'cardVal': 9}, {'suit': 'd', 'value': 'T', 'cardVal': 10}, {'suit': 's', 'value': 'K', 'cardVal': 13}], 'size': 3}
        game_log = {'size': 6, 'logs': [{'action': 'BET_MEDIUM', 'size': 3.0}, {'action': 'BET_MEDIUM', 'size': 10.0}, {'action': 'CALL', 'size': 10.0}, {'action': 'CHECK', 'size': 0.0}, {'action': 'BET_SMALL', 'size': 15.0}, {'action': 'ALL_IN', 'size': 90.0}]}
        input_tensor = input_to_tensor(hand, board, game_log)

        # Assert that the tensor has the correct length (187 in your case)
        self.assertEqual(input_tensor.size(0), 187)
        
        # Additional checks can be added to verify the contents of the tensor

if __name__ == '__main__':
    unittest.main()

