#from distutils.log import debug
#from crypt import methods
#from crypt import methods
from flask import Flask, jsonify, request, render_template
import os
import json
import datetime
import hashlib
import requests
from uuid import uuid4
from urllib.parse import urlparse

app = Flask(__name__)

/*
@app.route("/files", methods=['GET'])
def home():
    if(request.method == 'GET'):
        # res = jsonify('hell')#jsonify({'data':'hello'})
        res = jsonify({'files': os.listdir()})
        res.headers.add("Access-Control-Allow-Origin", "*")
        #dat = os.listdir()
        #res = render_template('home.html' , dat=dat)
        return res
*/

node_address = str(uuid4()).replace('-', '')


@app.route("/mine_block", methods=['GET'])
def mine_block():
    previous_block = blockchain.get_previous_block()
    previous_proof = previous_block['proof']
    proof = blockchain.proof_of_work(previous_proof)
    previous_hash = blockchain.hash(previous_block)
    blockchain.add_transaction(
        sender=node_address, receiver='Hadelin', amount=1)
    block = blockchain.create_block(proof, previous_hash)
    response = {'message': 'congo , you just mined a block',
                'index': block['index'],
                'timestamp': block['timestamp'],
                'proof': block['proof'],
                'previous_hash': block['previous_hash'],
                'transactions': block['transactions']}
    res = jsonify(response)
    res.headers.add("Access-Control-Allow-Origin", "*")
    return res


@app.route('/get_chain', methods=['GET'])
def get_chain():
    response = {'chain': blockchain.chain,
                'length': len(blockchain.chain)}
    res = jsonify(response)
    res.headers.add("Access-Control-Allow-Origin", "*")
    return res


@app.route('/is_valid', methods=['GET'])
def is_valid():
    is_valid = blockchain.is_chain_valid(blockchain.chain)
    return jsonify('Blockchain is valid ' if is_valid else 'Not a valid Blockchain'), 200


@app.route('/add_transaction', methods=['POST'])
def add_transaction():
    json = request.get_json()
    transaction_keys = ['sender', 'receiver', 'amount']
    if not all(key in json for key in transaction_keys):
        return 'Some elements of the transaction are missing', 400
    index = blockchain.add_transaction(
        json['sender'], json['receiver'], json['amount'])
    response = {'message': f'This transaction will be added to Block {index}'}
    return jsonify(response), 201


@app.route('/connect_node', methods=['POST'])
def connect_node():
    json = request.get_json()
    nodes = json.get('nodes')
    if nodes is None:
        return "No Node", 400
    for node in nodes:
        blockchain.add_node(node)
    response = {'message': 'All nodes are now connected . The RoCoin now contains these nodes',
                'total_nodes': list(blockchain.nodes)}
    return jsonify(response), 201


@app.route('/replace_chain', methods=['GET'])
def replace_chain():
    is_chain_replaced = blockchain.replace_chain()
    return jsonify({'message': 'Chain was replaced by other longest chain ', 'new_chain': blockchain.chain} if is_chain_replaced else {'message': 'All good. The chain is longest one', 'new_chain': blockchain.chain}), 200


class Blockchain:

    def __init__(self):

        self.chain = []
        self.transactions = []
        self.create_block(proof=1, previous_hash='0')
        self.nodes = set()

    def create_block(self, proof, previous_hash):
        block = {'index': len(self.chain)+1,
                 'timestamp': str(datetime.datetime.now()),
                 'proof': proof,
                 'previous_hash': previous_hash,
                 'transactions': self.transactions}
        self.transactions = []
        self.chain.append(block)
        return block

    def get_previous_block(self):
        return self.chain[-1]

    def proof_of_work(self, previous_proof):
        new_proof = 1
        check_proof = False
        while check_proof == False:
            hash_operation = hashlib.sha256(
                str(new_proof**2 - previous_proof**2).encode()).hexdigest()
            if(hash_operation[:4] == '0000'):
                check_proof = True
            else:
                new_proof += 1
        return new_proof

    def hash(self, block):
        encoded_block = json.dumps(block, sort_keys=True).encode()
        return hashlib.sha256(encoded_block).hexdigest()

    def is_chain_valid(self, chain):
        previous_block = chain[0]
        block_index = 1
        while block_index < len(chain):
            block = chain[block_index]
            if(block['previous_hash'] != self.hash(previous_block)):
                return False
            previous_proof = previous_block['proof']
            proof = block['proof']
            hash_operation = hashlib.sha256(
                str(proof**2 - previous_proof**2).encode()).hexdigest()
            if(hash_operation[:4] != '0000'):
                return False
            previous_block = block
            block_index += 1
        return True

    def add_transaction(self, sender, receiver, amount):
        self.transactions.append({'sender': sender,
                                  'receiver': receiver,
                                  'amount': amount})
        previous_block = self.get_previous_block()
        return previous_block['index'] + 1

    def add_node(self, address):
        parsed_url = urlparse(address)
        self.nodes.add(parsed_url.netloc)

    def replace_chain(self):
        network = self.nodes
        longest_chain = None
        max_length = len(self.chain)
        for node in network:
            response = requests.get(f'http://{node}/get_chain')
            if response.status_code == 200:
                length = response.json()['length']
                chain = response.json()['chain']
                if length > max_length and self.is_chain_valid(chain):
                    max_length = length
                    longest_chain = chain
        if longest_chain:
            self.chain = longest_chain
            return True
        return False


if __name__ == '__main__':
    blockchain = Blockchain()
    app.run(debug=True)
