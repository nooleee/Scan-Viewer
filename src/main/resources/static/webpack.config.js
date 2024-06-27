const path = require('path');
const NodePolyfillPlugin = require("node-polyfill-webpack-plugin");

module.exports = {
    entry: './script/cornerstone.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist'),
    },
    resolve: {
        modules: ['node_modules'],
        extensions: ['.ts', '.js', '.json', '.wasm']
    },
    cache: {
        type: 'filesystem',
        cacheDirectory: path.resolve(__dirname, '.webpack_cache'),
    },
    mode: 'development',

    experiments: {
        asyncWebAssembly: true // 또는 syncWebAssembly: true
    },
    module: {
        rules: [
            {
                test: /\.wasm$/,
                type: 'webassembly/async' // 또는 'webassembly/sync'
            },
            {
                test: /\.js$/,
                use: 'babel-loader',
                exclude: /node_modules/
            },
            {
                test: /\.wasm$/,
                use: 'file-loader',
                type: 'javascript/auto'
            }
        ]
    },
    plugins: [
        new NodePolyfillPlugin()
    ]


};